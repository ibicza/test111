package business.hub.authenticationservice.service;

import business.hub.authenticationservice.exception.InvalidTokenException;
import business.hub.authenticationservice.jwt_utils.AccessTokenUtil;
import business.hub.authenticationservice.jwt_utils.RefreshTokenUtil;
import business.hub.authenticationservice.dto.UserDTO;
import business.hub.authenticationservice.dto.AuthenticationRequest;
import business.hub.authenticationservice.dto.AuthenticationResponse;
import business.hub.authenticationservice.repositry.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Сервис для аутентификации пользователей с помощью JWT токенов.
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {

    /**
     * Репозиторий для работы с пользователями.
     */
    private final UserRepository userRepository;

    /**
     * Менеджер аутентификации Spring Security для аутентификации пользователей.
     */
    @Lazy
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * Утилита для работы с JWT токенами.
     */
    private final AccessTokenUtil accessTokenUtil;

    /**
     * Утилита для обновления JWT токенов.
     */
    private final RefreshTokenUtil refreshTokenUtil;

    /**
     * Имя токена обновления.
     */
    public static final String REFRESH_TOKEN_NAME = "refreshToken";
    /**
     * Имя токена доступа.
     */
    public static final String ACCESS_TOKEN_NAME = "accessToken";

    /**
     * Логгер для записи информации о действиях.
     */
    private final Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

    /**
     * Аутентификация пользователя и генерация токенов доступа и обновления.
     *
     * @param request объект запроса на аутентификацию {@link AuthenticationRequest}
     * @return объект ответа с сгенерированными токенами
     */
    public AuthenticationResponse authenticate(final AuthenticationRequest request) {
        logger.debug("Entering authenticate method");
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()));

        if (authentication == null) {
            return null;
        }

        Optional<UserDTO> userDTO = userRepository.findByUsername(request.getUsername());

        return userDTO.map(this::generateAndPersistTokens).orElse(null);
    }

    /**
     * Обновление токенов на основе обновленного токена.
     *
     * @param refreshToken обновленный токена {@link RefreshTokenUtil}
     * @return объект ответа с новыми токенами
     * @throws InvalidTokenException если токен обновления недействителен
     */
    public AuthenticationResponse refresh(final String refreshToken) {
        logger.debug("Entering refresh method");
        Optional<UserDTO> user = userRepository.findByRefreshToken(refreshToken);
        if (user.isEmpty()) {
            throw new InvalidTokenException("Bad refresh token. Log in again");
        }

        if (!refreshTokenUtil.isTokenValid(refreshToken, user.get())) {
            throw new InvalidTokenException("Token validation failed. Log in again");
        }

        return generateAndPersistTokens(user.get());
    }

    /**
     * Генерация новых токенов и сохранение их в базе данных.
     *
     * @param user информация о пользователе {@link UserDTO}
     * @return объект ответа с сгенерированными токенами {@link AuthenticationResponse}
     */
    private AuthenticationResponse generateAndPersistTokens(final UserDTO user) {
        String newAccessToken = accessTokenUtil.generateToken(user);
        String newRefreshToken = refreshTokenUtil.generateToken(user);

        user.setAccessToken(newAccessToken);
        user.setRefreshToken(newRefreshToken);
        userRepository.save(user);

        return AuthenticationResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
