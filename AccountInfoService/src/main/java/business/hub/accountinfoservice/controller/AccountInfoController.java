package business.hub.accountinfoservice.controller;

import business.hub.accountinfoservice.dto.AccountDTO;
import business.hub.accountinfoservice.service.AccountInfoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

/**
 * RestController Класс.
 * <p>
 * Этот класс предоставляет API для получения информации об аккаунте,
 * такой как электронная почта, на основе ID аккаунта.
 * </p>
 *
 * <p>
 * Пример запроса:
 * <pre>{@code
 * GET /api/inner/accounts/email?accountId={accountId}
 * }</pre>
 * </p>
 *
 * <p>
 * Коды состояния ответа:
 * <ul>
 *     <li>{@code 200 OK} - Успешное извлечение электронной почты</li>
 *     <li>{@code 404 NOT_FOUND} - Аккаунт с предоставленным ID не найден</li>
 * </ul>
 * </p>
 *
 * @version 1.0
 * @since 2024-04-23
 */
@RestController
@Tag(name = "main_methods")
@RequiredArgsConstructor
@RequestMapping("/api/inner/accounts")
@Slf4j
public class AccountInfoController {

    /**
     * Service, предоставляющий бизнес-логику для операций с аккаунтами.
     */
    private final AccountInfoService accountInfoService;

    /**
     * Возвращает электронную почту, на основе ID аккаунта.
     *
     * @param accountId ID аккаунта, для которого нужно извлечь электронную почту.
     * @return {@link ResponseEntity}, содержащий электронную почту и соответствующий
     * HTTP статус.
     */
    @GetMapping("/email")
    public ResponseEntity<String> getEmailByAccountId(
            final @RequestParam("accountId") String accountId) {
        try {
            String email = accountInfoService.getEmailByAccountId(accountId);
            return new ResponseEntity<>(email, HttpStatus.OK);
        } catch (AccountNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Создает новый аккаунт на основе предоставленных данных.
     *
     * @param accountDTO объект {@link AccountDTO}, содержащий информацию о новом аккаунте.
     * @return {@link ResponseEntity}, содержащий текстовое сообщение об успешном создании аккаунта и
     * соответствующий HTTP статус.
     */
    @PostMapping("/")
    public ResponseEntity<String> createAccount(final @RequestBody AccountDTO accountDTO) {
        log.info("Creating account: {}", accountDTO);
        accountInfoService.createAccount(accountDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Account created successfully");
    }
    /**
     * Возвращает информацию об аккаунте на основе его ID.
     *
     * @param accountId ID аккаунта, информацию о котором необходимо получить.
     * @return {@link ResponseEntity}, содержащий объект {@link AccountDTO} с информацией об аккаунте и
     * соответствующий HTTP статус.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDTO> getAccount(
            final @PathVariable(name = "accountId") Long accountId)
            throws AccountNotFoundException {
        log.info("Fetching account with ID: {}", accountId);
        AccountDTO accountDTO = accountInfoService.getAccountByAccountId(accountId);
        return ResponseEntity.ok(accountDTO);
    }
    /**
     * Обновляет информацию об аккаунте на основе предоставленных данных и ID аккаунта.
     *
     * @param accountDTO объект {@link AccountDTO}, содержащий обновленную информацию об аккаунте.
     * @param accountId  ID аккаунта, информацию о котором необходимо обновить.
     * @return {@link ResponseEntity}, содержащий текстовое сообщение об успешном обновлении информации об аккаунте и
     * соответствующий HTTP статус.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    @PutMapping("/{accountId}")
    public ResponseEntity<String> updateAccount(
            final @RequestBody AccountDTO accountDTO,
            final @PathVariable(name = "accountId") Long accountId)
            throws AccountNotFoundException {
        log.info("Updating account: {}", accountDTO);
        accountInfoService.updateAccount(accountDTO, accountId);
        return ResponseEntity.ok("Account updated successfully");
    }
    /**
     * Удаляет аккаунт на основе его ID.
     *
     * @param accountId ID аккаунта, который необходимо удалить.
     * @return {@link ResponseEntity}, содержащий текстовое сообщение об успешном удалении аккаунта и
     * соответствующий HTTP статус.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(
            final @PathVariable(name = "accountId") Long accountId)
            throws AccountNotFoundException {
        log.info("Deleting account with ID: {}", accountId);
        accountInfoService.deleteAccount(accountId);
        return ResponseEntity.ok("Account deleted successfully");
    }
}
