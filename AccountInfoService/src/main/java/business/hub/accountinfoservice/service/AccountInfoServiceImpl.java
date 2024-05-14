package business.hub.accountinfoservice.service;

import business.hub.accountinfoservice.dto.AccountDTO;
import business.hub.accountinfoservice.model.Account;
import business.hub.accountinfoservice.repository.AccountInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

/**
 * Класс реализации для {@link AccountInfoService}.
 * <p>
 * Этот класс предоставляет реализацию интерфейса {@link AccountInfoService},
 * обрабатывая операции, связанные с аккаунтами, такие как
 * извлечение электронной почты по ID аккаунта.
 * </p>
 *
 * <p>
 * Зависимости:
 * <ul>
 *     <li>{@link AccountInfoRepository} - Интерфейс репозитория
 *     для операций с базой данных</li>
 * </ul>
 * </p>
 *
 * @see AccountInfoService
 * @see AccountInfoRepository
 *
 * @version 1.0
 * @since 2024-04-23
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {

    /**
     * Интерфейс репозитория для операций с базой данных.
     */
    private final AccountInfoRepository accountInfoRepository;

    /**
     * Извлекает электронную почту, связанную с предоставленным ID аккаунта.
     *
     * @param accountId ID аккаунта, для которого
     *                 нужно извлечь электронную почту.
     * @return Электронная почта, связанная с ID аккаунта.
     * @throws AccountNotFoundException если аккаунт с предоставленным
     * ID не найден.
     */
    @Override
    public String getEmailByAccountId(final String accountId)
            throws AccountNotFoundException {
        Optional<Account> account =
                accountInfoRepository.findById(Long.parseLong(accountId));

        if (account.isPresent()) {
            return account.get().getEmail();
        } else {
            throw new AccountNotFoundException("Account not found with id " + accountId);
        }
    }

    /**
     * Создает новый аккаунт на основе предоставленных данных.
     *
     * @param accountDTO объект {@link AccountDTO}, содержащий информацию о новом аккаунте.
     * @return уникальный идентификатор созданного аккаунта.
     */
    @Override
    public Long createAccount(final AccountDTO accountDTO) {
        Account account = new Account();
        account.setEmail(accountDTO.getEmail());
        account.setPassport(accountDTO.getPassport());
        account = accountInfoRepository.save(account);
        log.info("Created an account entity with id {}", account.getId());
        return account.getId();
    }

    /**
     * Получает информацию об аккаунте по его идентификатору.
     *
     * @param accountId идентификатор аккаунта.
     * @return объект {@link AccountDTO} с информацией о запрошенном аккаунте.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    @Override
    public AccountDTO getAccountByAccountId(final Long accountId) {
        Account account = accountInfoRepository.findById(accountId).orElseThrow();
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setEmail(account.getEmail());
        accountDTO.setPassport(account.getPassport());
        log.info("Get an account entity with id {}", account.getId());
        return accountDTO;
    }

    /**
     * Обновляет информацию об аккаунте на основе предоставленных данных и ID аккаунта.
     *
     * @param accountDTO объект {@link AccountDTO}, содержащий обновленную информацию об аккаунте.
     * @param accountId  идентификатор аккаунта, информацию о котором необходимо обновить.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    @Override
    public void updateAccount(final AccountDTO accountDTO, final Long accountId) {
        Account account = accountInfoRepository.findById(accountId).orElseThrow();
        account.setEmail(accountDTO.getEmail());
        account.setPassport(accountDTO.getPassport());
        accountInfoRepository.save(account);
        log.info("Edited the entity with id {}", account.getId());
    }

    /**
     * Удаляет аккаунт по его идентификатору.
     *
     * @param accountId идентификатор аккаунта, который необходимо удалить.
     * @throws AccountNotFoundException если аккаунт с предоставленным ID не найден.
     */
    @Override
    public void deleteAccount(final Long accountId) {
        Account account = accountInfoRepository.findById(accountId).orElseThrow();
        accountInfoRepository.delete(account);
        log.info("Deleted the entity with id {}", account.getId());
    }
}
