package business.hub.authenticationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO-Класс, который отдает обновленный токен.
 * Имеет метод .getRefreshToken (с помощью @Data).
 * используется например в {@link
 * business.hub.authenticationservice.controller.AuthenticationRestController}
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshRequest {
    /**
     * Токен обновления, используемый для обновления токена доступа
     * после его истечения срока действия.
     */
    private String refreshToken;
}
