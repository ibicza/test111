package business.hub.services.account;

import business.hub.entitys.Account;
import business.hub.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Сервис по получению данных из базы.
 * @author Ostrovsky
 */
@Service
@Transactional
public class ReadAccountServiceImpl implements ReadAccountService {
    /**
     * Объявляем переменные accountRepository, logger.
     */
    private final AccountRepository accountRepository;
    private final Logger logger = LoggerFactory.getLogger(ReadAccountServiceImpl.class);

    /**
     * Констурктор для внедрения зависостей.
     * @param accountRepositoryParam внедряем зависимость
     */
    @Autowired
    public ReadAccountServiceImpl(final AccountRepository accountRepositoryParam) {
        this.accountRepository = accountRepositoryParam;
    }

    /**
     * Метод для получения списка всех сущностей account.
     * @return возвращет полученный список из базы
     */
    @Override
    public List<Account> getAllAccount() {

        List<Account> allAccounts = accountRepository.findAll();

        logger.info("Retrieved all accounts. Count: {}", allAccounts.size());

        return allAccounts;
    }

    /**
     *
     * @param email использует email для получения account.
     * @return возвращает найденный account
     */
    @Override
    public Account getAccountByEmail(final String email) {

        Account emailAccount = accountRepository.findByEmail(email);

        if (emailAccount == null) {
            logger.warn("Account not found for email: {}", email);

            throw new IllegalArgumentException("Account not found");
        }

        logger.info("Account found by email: {}", emailAccount.getEmail());

        return emailAccount;
    }

    /**
     * Метод для поиска account по ID.
     * @param id использует id для получения account
     * @return возвращает сущьность найденную по id
     */
    @Override
    public Account getAccountById(final Long id) {
        Account accountById = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        logger.info("Account found by ID: {}", accountById.getAccountId());

        return accountById;
    }
}
