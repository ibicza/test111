package business.hub.cleaningrefreshtokensservice.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Класс для обновления токена.
 */
@Entity
@Data
public class RefreshToken {
    @Id
    private String token;
    private LocalDateTime expiration;
}
