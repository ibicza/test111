package business.hub.authenticationservice.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * POJO-Класс для ответов на аутификацию.
 * Имеет методы .getUsername и .getPassword (с помощью @Data).
 * используется например в {@link
 * business.hub.authenticationservice.controller.AuthenticationRestController}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {

    /**
     * Имя пользователя.
     */
    private String username;

    /**
     * Пароль пользователя.
     */
    private String password;
}
