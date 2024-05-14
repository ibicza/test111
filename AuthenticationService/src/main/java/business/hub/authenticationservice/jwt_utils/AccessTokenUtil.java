package business.hub.authenticationservice.jwt_utils;

import business.hub.authenticationservice.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Утилита для работы с токенами доступа.
 */

@Service
public class AccessTokenUtil extends AbstractTokenUtil {

    /**
     * Секретный ключ для подписи JWT токена доступа.
     */
    @Value("${jwt.accessTokenSecret}")
    private String accessTokenSecret;

    /**
     * Продолжительность жизни JWT токена доступа равна 1му дню.
     * Настройки можно посмотреть нажав по @Value()
     */
    @Value("${jwt.accessTokenLifetime}")
    private Duration accessTokenLifetime;

    /**
     * Извлечение имени пользователя из токена доступа.
     *
     * @param token JWT токен доступа
     * @return имя пользователя
     */
    @Override
    public String extractUsernameFromToken(final String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Извлечение данных из токена с использованием функции.
     *
     * @param token          JWT токен
     * @param claimsResolver функция для извлечения утверждения
     * @return значение утверждения
     */
    @Override
    public <T> T extractClaim(final String token,
                              final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, accessTokenSecret);
        return claimsResolver.apply(claims);
    }

    /**
     * Генерация JWT токена доступа для пользователя.
     *
     * @param userDTO информация о пользователе
     * @return сгенерированный JWT токен доступа
     */
    @Override
    public String generateToken(final UserDTO userDTO) {
        Map<String, Object> extraClaims = new HashMap<>();
        List<String> listRoles = userDTO.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).toList();
        extraClaims.put("id", userDTO.getId());
        extraClaims.put("roles", listRoles);
        return generateToken(extraClaims, userDTO);
    }
    /**
     * Проверка валидности токена.
     *
     * @param token JWT токен
     * @param user  детали пользователя
     * @return true, если токен валиден, иначе false
     */
    @Override
    public boolean isTokenValid(final String token, final UserDetails user) {
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
                        + accessTokenLifetime.toMillis()))
                .signWith(getSigningKey(accessTokenSecret), SignatureAlgorithm.HS256)
                .compact();
    }
}

