package business.hub.authenticationservice.jwt_utils;


import business.hub.authenticationservice.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Утилита для работы с JWT токенами обновления.
 */

@Component
public class RefreshTokenUtil extends AbstractTokenUtil {

    /**
     * Продолжительность жизни JWT токена обновления - 30 дней.
     * Настройки можно посмотреть нажав по @Value()
     */
    @Value("${jwt.refreshTokenLifetime}")
    private Duration refreshTokenLifetime;

    /**
     * Секретный ключ для подписи JWT токена обновления.
     */
    @Value("${jwt.refreshTokenSecret}")
    private String refreshTokenSecret;

    /**
     * Извлечение имени пользователя из токена обновления.
     *
     * @param token JWT токен обновления
     * @return имя пользователя
     */
    @Override
    public String extractUsernameFromToken(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлечение данных из токена.
     *
     * @param token JWT токен
     * @param claimsResolver функция для извлечения данных
     * @return значение данных
     */
    @Override
    public <T> T extractClaim(final String token,
                              final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, refreshTokenSecret);
        return claimsResolver.apply(claims);
    }

    /**
     * Генерация JWT токена обновления для пользователя.
     *
     * @param userDTO информация о пользователе
     * @return сгенерированный JWT токен обновления
     */
    @Override
    public String generateToken(final UserDTO userDTO) {
        Map<String, Object> extraClaims = new HashMap<>();
        List<String> listRoles = userDTO.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        extraClaims.put("id", userDTO.getId());
        extraClaims.put("roles", listRoles);
        return generateToken(extraClaims, userDTO);
    }

    /**
     * Проверка валидности токена обновления.
     *
     * @param token JWT токен обновления
     * @param user  детали пользователя
     * @return true, если токен валиден, иначе false
     */
    @Override
    public boolean isTokenValid(final String token,
                                final UserDetails user) {
        String username = extractUsernameFromToken(token);
        return username.equals(user.getUsername()) && !isTokenNotExpired(token);
    }

    /**
     * Генерация JWT токена с дополнительными данными и Username пользователя.
     *
     * @param extraClaims  дополнительные утверждения
     * @param userDetails детали пользователя
     * @return сгенерированный JWT токен
     */
    private String generateToken(final Map<String, Object> extraClaims,
                                 final UserDetails userDetails) {
    return Jwts
            .builder()
            .setClaims(extraClaims)
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis()
                    + refreshTokenLifetime.toMillis()))
            .signWith(getSigningKey(refreshTokenSecret))
            .compact();
    }
}
