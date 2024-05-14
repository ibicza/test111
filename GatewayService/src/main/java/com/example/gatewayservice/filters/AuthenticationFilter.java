package com.example.gatewayservice.filters;


import com.example.gatewayservice.Util.PathFilterUtil;
import com.example.gatewayservice.dto.AuthenticationResponse;
import com.example.gatewayservice.dto.RefreshRequest;
import com.example.gatewayservice.jwt.JwtUtil;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.nio.file.AccessDeniedException;
import java.time.Duration;
import java.util.List;

/**
 * Фильтр аутентификации для обработки запросов.
 */
@Component
public class AuthenticationFilter implements GatewayFilter {
    private static final int REFRESH_TOKEN_EXPIRATION_DAYS = 30;

    private final KafkaTemplate kafkaTemplate;

    private final JwtUtil jwtUtil;
    private final PathFilterUtil pathFilterUtil;

    private final WebClient webClient;
    private String roles;
    private int userId;
    private String username;
    private final Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);


    /**
     * Конструктор класса AuthenticationFilter.
     *
     * @param kafkaTemplateParam             шаблон Kafka для отправки сообщений
     * @param jwtUtilParam                   утилита для работы с JWT-токенами
     * @param webClientAuthenticationParam  WebClient для выполнения аутентификационных запросов
     * @param pathFilterUtilParam            утилита для фильтрации путей
     */
    public AuthenticationFilter(final KafkaTemplate kafkaTemplateParam,
                                final JwtUtil jwtUtilParam,
                                final WebClient webClientAuthenticationParam,
                                final PathFilterUtil pathFilterUtilParam) {
        this.kafkaTemplate = kafkaTemplateParam;
        this.jwtUtil = jwtUtilParam;
        this.webClient = webClientAuthenticationParam;
        this.pathFilterUtil = pathFilterUtilParam;
    }

    /**
     * Фильтрует входящие HTTP-запросы и обрабатывает аутентификацию пользователей.
     *
     * @param exchange   объект обмена между клиентом и сервером
     * @param chain      цепочка фильтров
     * @return           результат обработки запроса
     */
    @Override
    public Mono<Void> filter(final ServerWebExchange exchange,
                             final GatewayFilterChain chain) {
        String accessToken = extractValueFromCookie(exchange.getRequest(),
                "accessToken");
        if (accessToken == null) {
            logger.info("Access token is absent in cookie. Access denied");
            return setUnauthorizedResponse(exchange,
                    "Access token is absent in cookie. Access denied");
        }

        try {
            jwtUtil.validateToken(accessToken);

            List<String> listRoles = jwtUtil.extractRoles(accessToken);
            String path = exchange.getRequest().getURI().getPath();

            if (!pathFilterUtil.isAccessAllowed(path, listRoles)) {
                return setUnauthorizedResponse(exchange,
                        "Access denied for this role.");
            }
        } catch (ExpiredJwtException e) {
            logger.debug("User with expired access token");
            return handleExpiredAccessToken(exchange)
                    .flatMap(authResp -> {
                        setAuthenticationCookies(authResp, exchange);
                        sendUserInfoToKafka(authResp.getAccessToken());
                        return chain.filter(exchange);
                    })
                    .doOnError(err -> logger.debug(err.getMessage()))
                    .onErrorResume(err -> setUnauthorizedResponse(exchange,
                            "Failed to refresh tokens. Access denied. Log in again."));

        } catch (MalformedJwtException | SignatureException | IllegalArgumentException e) {
            logger.info(e.getMessage());
            return setUnauthorizedResponse(exchange, e.getMessage());
        }
        sendUserInfoToKafka(accessToken);
        return chain.filter(exchange);
    }

    /**
     * Обрабатывает ситуацию с истекшим доступным токеном.
     *
     * @param exchange   объект обмена между клиентом и сервером
     * @return           монопоток с информацией об обновленных токенах аутентификации
     */
    private Mono<AuthenticationResponse> handleExpiredAccessToken(
            final ServerWebExchange exchange) {
        String refreshToken = extractValueFromCookie(exchange.getRequest(),
                "refreshToken");
        if (refreshToken == null) {
            logger.info("Refresh token is absent in cookie. Access denied");
            return Mono.error(new AccessDeniedException(
                    "Refresh token is absent in cookie. Access denied"));
        }
        logger.debug("Sending refresh request");

        return webClient.post()
                .uri("http://localhost:50500/api/auth/refreshTokens")
                .body(Mono.just(new RefreshRequest(refreshToken)),
                        RefreshRequest.class)
                .retrieve()
                .bodyToMono(AuthenticationResponse.class)
                .doOnSuccess(resp -> logger.debug("Successful refresh request. %n"
                         + "Access token - {} %n"
                         + "Refresh token - {} %n ", resp.getAccessToken(),
                        resp.getRefreshToken()));
    }

    /**
     * Устанавливает куки с новыми токенами аутентификации.
     *
     * @param authResp   объект с новыми токенами аутентификации
     * @param exchange   объект обмена между клиентом и сервером
     */
    public void setAuthenticationCookies(final AuthenticationResponse authResp,
                                         final ServerWebExchange exchange) {

        logger.debug("Setting up new access and refresh tokens in cookies");
        exchange.getResponse().addCookie(
                ResponseCookie.from("accessToken",
                        authResp.getAccessToken()).maxAge(Duration.ofDays(1)).build());

        exchange.getResponse().addCookie(
                ResponseCookie.from("refreshToken",
                        authResp.getRefreshToken()).maxAge(
                                Duration.ofDays(REFRESH_TOKEN_EXPIRATION_DAYS)).build());
    }

    /**
     * Извлекает значение из cookie запроса.
     *
     * @param request    объект запроса
     * @param cookieName имя cookie
     * @return значение cookie или null, если cookie отсутствует
     */
    private String extractValueFromCookie(final ServerHttpRequest request,
                                          final String cookieName) {
        MultiValueMap<String, HttpCookie> cookies = request.getCookies();
        HttpCookie cookie = cookies.getFirst(cookieName);
        logger.debug("Retrieved a {} from request - {}", cookieName, cookie);
        if (cookie == null) {
            return null;
        }
        return cookie.getValue();
    }

    /**
     * Устанавливает ответ с кодом UNAUTHORIZED и сообщением об ошибке.
     *
     * @param exchange объект обмена между клиентом и сервером
     * @param message  сообщение об ошибке
     * @return монопоток с результатом записи ответа
     */
    private Mono<Void> setUnauthorizedResponse(final ServerWebExchange exchange,
                                               final String message) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        DataBuffer dataBuffer = response.bufferFactory().wrap(
                message.getBytes(StandardCharsets.UTF_8));

        return response.writeWith(Mono.just(dataBuffer))
                .doOnError(error -> DataBufferUtils.release(dataBuffer));
    }
    /**
     * Отправляет информацию о пользователе в Kafka.
     *
     * @param accessToken токен доступа пользователя
     */
    public void sendUserInfoToKafka(final String accessToken) {
        username = jwtUtil.extractUsername(accessToken);
        userId = jwtUtil.extractUserId(accessToken);
        roles = String.valueOf(jwtUtil.extractRoles(accessToken));
        kafkaTemplate.send("id-topic", String.valueOf(userId));
        kafkaTemplate.send("username-topic", username);
        kafkaTemplate.send("roles-topic", roles);
    }
}
