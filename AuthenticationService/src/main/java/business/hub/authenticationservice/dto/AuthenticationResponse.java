package business.hub.authenticationservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO-Класс ответа на запрос аутификации.
 * Имеет методы .getAccessToken и .getRefreshToken (с помощью @Data).
 * используется например в {@link
 * business.hub.authenticationservice.controller.AuthenticationRestController}
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

    /**
     * Токен доступа, используемый для аутентификации пользователя.
     */
    private String accessToken;

    /**
     * Токен обновления, используемый для обновления токена доступа
     * после его истечения срока действия.
     */
    private String refreshToken;
}
