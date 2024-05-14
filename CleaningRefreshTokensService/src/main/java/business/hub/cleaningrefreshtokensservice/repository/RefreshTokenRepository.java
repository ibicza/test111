package business.hub.cleaningrefreshtokensservice.repository;

import business.hub.cleaningrefreshtokensservice.domain.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

/**
 * Методы для RefreshTokenRepository с использованием JpaRepository.
 */
@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, String> {
    /**
     * Удаление по истечении строка токена.
     * @param expiration
     */
    void deleteByExpirationBefore(LocalDateTime expiration);
}
