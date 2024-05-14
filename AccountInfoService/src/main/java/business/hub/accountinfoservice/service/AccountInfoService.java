package business.hub.accountinfoservice.service;

import business.hub.accountinfoservice.dto.AccountDTO;

import javax.security.auth.login.AccountNotFoundException;

/**
 * Интерфейс для управления операциями, связанными с аккаунтами.
 * <p>
 * Этот интерфейс определяет методы для извлечения информации об аккаунте,
 * такой как электронная почта, на основе предоставленного ID аккаунта.
 * </p>
 *
 * <p>
 * Методы:
 * <ul>
 *     <li>{@code getEmailByAccountId} - Извлекает электронную почту,
 *     связанную с предоставленным ID аккаунта</li>
 * </ul>
 * </p>
 *
 * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
 *
 * @version 1.0
 * @since 2024-04-23
 */
public interface AccountInfoService {
    /**
     * Извлекает электронную почту, по ID аккаунта.
     *
     * @param accountId ID аккаунта.
     * @return Электронная почта, связанная с ID аккаунта.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    String getEmailByAccountId(String accountId) throws AccountNotFoundException;

    /**
     * Создает новый аккаунт на основе предоставленных данных.
     *
     * @param accountDTO объект {@link AccountDTO}, содержащий информацию о новом аккаунте.
     * @return уникальный идентификатор созданного аккаунта.
     */
    Long createAccount(AccountDTO accountDTO);

    /**
     * Получает информацию об аккаунте по его идентификатору.
     *
     * @param accountId идентификатор аккаунта.
     * @return объект {@link AccountDTO} с информацией о запрошенном аккаунте.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    AccountDTO getAccountByAccountId(Long accountId)
            throws AccountNotFoundException;

    /**
     * Обновляет информацию об аккаунте на основе предоставленных данных и ID аккаунта.
     *
     * @param accountDTO объект {@link AccountDTO}, содержащий обновленную информацию об аккаунте.
     * @param accountId  идентификатор аккаунта, информацию о котором необходимо обновить.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    void updateAccount(AccountDTO accountDTO, Long accountId)
            throws AccountNotFoundException;

    /**
     * Удаляет аккаунт по его идентификатору.
     *
     * @param accountId идентификатор аккаунта, который необходимо удалить.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    void deleteAccount(Long accountId)
            throws AccountNotFoundException;

}
