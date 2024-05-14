package business.hub.userinfoservice.service;

import business.hub.userinfoservice.client.AuthFeignClient;
import business.hub.userinfoservice.dto.UserDto;
import business.hub.userinfoservice.model.User;
import business.hub.userinfoservice.repository.UserRepository;
import feign.FeignException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Сервис для получения информации о пользователях.
 * Обеспечивает взаимодействие с репозиторием пользователей и Feign-клиентом для сервиса аутентификации.
 */
@Service
public class UserInfoService {

    private final UserRepository userRepository;
    private final AuthFeignClient authFeignClient;

    /**
     * Конструктор сервиса с инъекцией зависимостей.
     *
     * @param userRepositoryParam Репозиторий пользователей.
     * @param authFeignClientParam Feign-клиент для сервиса аутентификации.
     */
    public UserInfoService(final UserRepository userRepositoryParam,
                           final AuthFeignClient authFeignClientParam) {
        this.userRepository = userRepositoryParam;
        this.authFeignClient = authFeignClientParam;
    }

    /**
     * Получение информации о пользователях по списку идентификаторов аккаунтов.
     *
     * @param accountIds Список идентификаторов аккаунтов.
     * @return Список пользовательских DTO.
     */
    public List<UserDto> getUserInfo(final List<Long> accountIds) {
        try {
            List<User> users = userRepository.findUsersByIdIn(accountIds);
            List<String> emails = users.stream().map(User::getEmail).collect(Collectors.toList());
            return authFeignClient.getUserDetailsByEmails(emails);
        } catch (FeignException e) {
            handleFeignException(e);
        } catch (Exception e) {
            handleOtherExceptions(e);
        }
        return null;
    }

    /**
     * Обработчик исключений FeignException.
     *
     * @param e Исключение FeignException.
     * @return ResponseEntity с кодом ошибки и сообщением об ошибке.
     */
    @ExceptionHandler(FeignException.class)
    public ResponseEntity<String> handleFeignException(final FeignException e) {
        return ResponseEntity.status(e.status()).body(e.getMessage());
    }

    /**
     * Обработчик прочих исключений.
     *
     * @param e Прочее исключение.
     * @return ResponseEntity с кодом ошибки HttpStatus.INTERNAL_SERVER_ERROR и сообщением "Internal Server Error".
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleOtherExceptions(final Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal Server Error");
    }
}
