package business.hub.authenticationservice.jwt_utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

import java.security.Key;
import java.util.Date;

/**
 * Абстрактный класс для утилиты работы с JWT токенами.
 */
public abstract class AbstractTokenUtil implements TokenUtil {

    /**
     * Проверка, не истек ли срок действия токена.
     *
     * @param token JWT токен
     * @return true, если токен не истек, иначе false
     */
    protected boolean isTokenNotExpired(final String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    /**
     * Извлечение даты истечения срока действия токена.
     *
     * @param token JWT токен
     * @return дата истечения срока действия токена
     */
    protected Date extractExpiration(final String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Извлечение всех утверждений из JWT токена.
     *
     * @param token     JWT токен
     * @param secretKey секретный ключ для подписи токена
     * @return утверждения из токена
     * @throws ExpiredJwtException       исключение, если токен истек
     * @throws UnsupportedJwtException   исключение, если токен не поддерживается
     * @throws MalformedJwtException     исключение, если токен имеет неверный формат
     * @throws SignatureException        исключение, если токен имеет неверную подпись
     * @throws IllegalArgumentException исключение, если аргументы неверны
     */
    protected Claims extractAllClaims(final String token, final String secretKey) throws
            ExpiredJwtException,
            UnsupportedJwtException,
            MalformedJwtException,
            SignatureException,
            IllegalArgumentException {
        return Jwts
                .parser()
                .setSigningKey(getSigningKey(secretKey))
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * Получение ключа для подписи токена.
     *
     * @param secretKey секретный ключ в формате BASE64
     * @return ключ для подписи токена
     */
    protected Key getSigningKey(final String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
