package business.hub.userinfoservice.client;

import business.hub.userinfoservice.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
/**
 * Feign-клиент для взаимодействия с сервисом аутентификации.
 */
@FeignClient(name = "auth-service")
public interface AuthFeignClient {

    /**
     * Получение информации о пользователях по списку email.
     *
     * @param emails Список email пользователей.
     * @return Список информации о пользователях.
     */
    @PostMapping("/api/inner/userinfo")
    List<UserDto> getUserDetailsByEmails(@RequestBody List<String> emails);

    /**
     * Получение email по идентификатору аккаунта.
     *
     * @param accountId Идентификатор аккаунта.
     * @return Email связанный с указанным аккаунтом.
     */
    @GetMapping("/api/inner/accounts/email")
    String getEmailByAccountId(@RequestParam("accountId") String accountId);
}
