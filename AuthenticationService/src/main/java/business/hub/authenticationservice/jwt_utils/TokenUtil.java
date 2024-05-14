package business.hub.authenticationservice.jwt_utils;

import business.hub.authenticationservice.dto.UserDTO;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.function.Function;

/**
 * Интерфейс для утилиты работы с JWT токенами.
 */
public interface TokenUtil {
    /**
     * Извлечение имени пользователя из токена.
     *
     * @param token JWT токен
     * @return имя пользователя
     */
    String extractUsernameFromToken(String token);

    /**
     * Извлечение данных из токена с использованием функции.
     *
     * @param token          JWT токен
     * @param claimsResolver функция для извлечения утверждения
     * @param <T>            тип утверждения
     * @return значение утверждения
     */
    <T> T extractClaim(String token, Function<Claims, T> claimsResolver);

    /**
     * Генерация JWT токена для пользователя.
     *
     * @param userDTO информация о пользователе
     * @return сгенерированный JWT токен
     */
    String generateToken(UserDTO userDTO);

    /**
     * Проверка валидности токена для указанного пользователя.
     *
     * @param token JWT токен
     * @param user  детали пользователя
     * @return true, если токен валиден, иначе false
     */
    boolean isTokenValid(String token, UserDetails user);
}
