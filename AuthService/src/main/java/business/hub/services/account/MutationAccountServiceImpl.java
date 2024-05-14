package business.hub.services.account;

import business.hub.dto.AccountDto;
import business.hub.entitys.Account;
import business.hub.repository.AccountRepository;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Сервис внесения изменений.
 * @author Ostrovsky
 */
@Service
@Transactional
public class MutationAccountServiceImpl implements MutationAccountService {
    /**
     * Объявляем переменные passwordEncoder, accountRepository, logger.
     */
    private final PasswordEncoder passwordEncoder;
    private final AccountRepository accountRepository;
    private Logger logger = LoggerFactory.getLogger(MutationAccountServiceImpl.class);

    /**
     * @param passwordEncoderParam вводим зависмость через конструктор.
     * @param accountRepositoryParam вводим зависимость через конструктор
     */
    @Autowired
    public MutationAccountServiceImpl(final PasswordEncoder passwordEncoderParam,
                                      final AccountRepository accountRepositoryParam) {
        this.passwordEncoder = passwordEncoderParam;
        this.accountRepository = accountRepositoryParam;
    }

    /**
     * Метод для установки мокированного logger в tests.
     * @param mockLogger logger
     */
    public void setLogger(final Logger mockLogger) {
        this.logger = mockLogger;
    }

    /**
     * Метод сохранения.
     * @param account сущность для сохранения
     */
    @Override
    public void save(@Valid final Account account) {
        /*
         * устанавливаем и кодируем пароль
         */
        account.setPassword(passwordEncoder.encode(account.getPassword()));
        accountRepository.save(account);
        /*
         * производим логирование сохранения
         */
        logger.info("Account saved: {}", account.getEmail());
    }

    /**
     * Метод редактирования.
     * @param id для поиска сущности в базе данных
     * @param dto для редактирования полей сущности
     */
    @Override
    public void edit(final Long id, final AccountDto dto) {
        //Getting account by id from the repository
        /*
         * Извлекаем account по id если не находим выбрасываем ошибку "Account not found".
         */
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        //update fields
        existingAccount.setEmail(dto.getEmail());
        existingAccount.setPassword(passwordEncoder.encode(dto.getPassword()));
        existingAccount.setRegistrationDate(dto.getRegistrationDate());
        existingAccount.setRoles(dto.getRoles());
        //saving
        accountRepository.save(existingAccount);

        logger.info("Account edited: {}", existingAccount.getEmail());
    }

    /**
     * Метод удаления.
     * @param id для поиска и удаления сущности
     */
    @Override
    public void delete(final Long id) {
        //Getting account from repository by ID
        Account existingAccount = accountRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));
        //deleting account
        accountRepository.delete(existingAccount);

        logger.info("Account deleted: {}", existingAccount.getAccountId());
    }
}
