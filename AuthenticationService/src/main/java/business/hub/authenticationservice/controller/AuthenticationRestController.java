package business.hub.authenticationservice.controller;

import business.hub.authenticationservice.dto.AuthenticationRequest;
import business.hub.authenticationservice.dto.AuthenticationResponse;
import business.hub.authenticationservice.dto.RefreshRequest;
import business.hub.authenticationservice.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

import static business.hub.authenticationservice.service.AuthenticationService.ACCESS_TOKEN_NAME;
import static business.hub.authenticationservice.service.AuthenticationService.REFRESH_TOKEN_NAME;

/**
 * Контроллер для обработки запросов аутентификации и обновления токенов.
 * Перейдя по /api/auth/authenticate - можно зарегестрироваться
 * Перейдя по /api/auth/refreshTokens - можно обновить токен
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth/")
public class AuthenticationRestController {
    /**
     * Сервис для аутентификации и обновления токенов.
     */
    private final AuthenticationService authenticationService;

    /**
     * Время жизни пожизненного токена обновления (в секундах).
     */
    @Value("${jwt.refreshTokenLifetime}")
    private Duration refreshTokenLifetime;

    /**
     * Аутентификация пользователя.
     *
     * @param request  объект с данными для аутентификации
     * {@link AuthenticationRequest} String username, String password
     * @param response объект ответа, в который будут добавлены куки с токенами
     * @return ответ с данными аутентификации пользователя -
     * {@link AuthenticationResponse} - String accessToken, String refreshToken
     */
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody final AuthenticationRequest request,
            final HttpServletResponse response) {
        AuthenticationResponse authenticationResponse =
                authenticationService.authenticate(request);
        putTokensInTheCookie(authenticationResponse, response);
        return ResponseEntity.ok(authenticationResponse);
    }

    /**
     * Обновление токенов.
     *
     * @param request - данные для обновления токенов -
     * {@link RefreshRequest} String refreshToken
     * @param response - объект HttpServletResponse
     * @return ResponseEntity с результатом обновления токенов -
     * {@link AuthenticationResponse} - String accessToken, String refreshToken
     */
    @PostMapping("/refreshTokens")
    public ResponseEntity<AuthenticationResponse> refreshTokens(
            @RequestBody final RefreshRequest request,
            final HttpServletResponse response) {
        AuthenticationResponse authenticationResponse =
                authenticationService.refresh(request.getRefreshToken());
        putTokensInTheCookie(authenticationResponse, response);
        return ResponseEntity.ok(authenticationResponse);
    }

    /**
     * Добавление токенов в Cookie.
     *
     * @param authRes - объект {@link AuthenticationResponse}
     * @param response - объект HttpServletResponse c новым Token в Cookie.
     */
    private void putTokensInTheCookie(
            final AuthenticationResponse authRes,
            final HttpServletResponse response) {
        Cookie acceessTokenCookie = new Cookie(ACCESS_TOKEN_NAME, authRes.getAccessToken());
        acceessTokenCookie.setMaxAge((int) refreshTokenLifetime.toSeconds());
        response.addCookie(acceessTokenCookie);

        Cookie refreshTokenCookie = new Cookie(REFRESH_TOKEN_NAME, authRes.getRefreshToken());
        refreshTokenCookie.setMaxAge((int) refreshTokenLifetime.toSeconds());
        response.addCookie(refreshTokenCookie);
    }

}
