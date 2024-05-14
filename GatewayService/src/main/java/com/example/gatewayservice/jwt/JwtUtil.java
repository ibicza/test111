package com.example.gatewayservice.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

/**
 * Утилитарный класс для работы с JWT-токенами.
 */
@Component
public class JwtUtil {

    private String accessTokenSecret = "808bd7b5b348ace7685435a9e88b3ba0e9bbdb33b83b8d6f3ed6e342837ee4cc";

    /**
     * Проверяет, действителен ли переданный JWT-токен.
     *
     * @param token JWT-токен для проверки
     * @return true, если токен действителен, иначе false
     */
    public boolean validateToken(final String token) {
        return isTokenNotExpired(token);
    }
    /**
     * Извлекает имя пользователя из JWT-токена.
     *
     * @param token JWT-токен
     * @return имя пользователя
     */
    public String extractUsername(final String token) {
        return extractClaim(token, Claims::getSubject);
    }
    /**
     * Извлекает роли пользователя из JWT-токена.
     *
     * @param token JWT-токен
     * @return список ролей пользователя
     */
    public List<String> extractRoles(final String token) {
        return extractClaim(token, claims -> claims.get("roles", List.class));
    }
    /**
     * Извлекает идентификатор пользователя из JWT-токена.
     *
     * @param token JWT-токен
     * @return идентификатор пользователя
     */
    public Integer extractUserId(final String token) {
        return extractClaim(token, claims -> claims.get("id", Integer.class));
    }
    /**
     * Проверяет, не истек ли срок действия переданного JWT-токена.
     *
     * @param token JWT-токен для проверки
     * @return true, если срок действия токена не истек, иначе false
     */
    private boolean isTokenNotExpired(final String token) {
        return extractExperationDate(token).before(new Date(System.currentTimeMillis()));
    }
    /**
     * Извлекает дату истечения срока действия JWT-токена.
     *
     * @param token JWT-токен
     * @return объект Date, представляющий дату истечения срока действия токена
     */
    private Date extractExperationDate(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }
    /**
     * Извлекает определенное утверждение (claim) из JWT-токена
     * с использованием переданного функционального интерфейса.
     *
     * @param token          JWT-токен
     * @param claimsResolver функциональный интерфейс для извлечения утверждения
     *                      из объекта Claims
     * @param <T>            тип утверждения, который требуется извлечь
     * @return извлеченное утверждение
     */
    public <T> T extractClaim(final String token,
                              final Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token, accessTokenSecret);
        return claimsResolver.apply(claims);
    }
    /**
     * Извлекает все утверждения из JWT-токена.
     *
     * @param token     JWT-токен
     * @param secretKey секретный ключ для проверки подписи токена
     * @return объект Claims, содержащий все утверждения из токена
     */
    private Claims extractAllClaims(final String token, final String secretKey) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    /**
     * Возвращает ключ подписи для JWT на основе переданного секретного ключа.
     *
     * @param secretKey секретный ключ для генерации ключа подписи
     * @return объект Key для подписи JWT
     */
    private Key getSigningKey(final String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
