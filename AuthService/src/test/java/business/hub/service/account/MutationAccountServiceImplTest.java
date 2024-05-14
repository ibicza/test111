package business.hub.service.account;

import business.hub.dto.AccountDto;
import business.hub.entitys.Account;
import business.hub.repository.AccountRepository;
import business.hub.services.account.MutationAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
 class MutationAccountServiceImplTest {
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Logger logger;
    @InjectMocks
    private MutationAccountServiceImpl mutationAccountService;

    private Account account;
    private AccountDto accountDto;


    /**
     * Настройка перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mutationAccountService = new MutationAccountServiceImpl(passwordEncoder, accountRepository);
        mutationAccountService.setLogger(logger);

        account = new Account();
        account.setEmail("account@mail.ru");
        account.setPassword("password");
        account.setRegistrationDate(LocalDateTime.now());

        accountDto = new AccountDto();
        accountDto.setEmail("update@mail.ru");
        accountDto.setPassword("updatePassword");
        accountDto.setRegistrationDate(LocalDateTime.now());
    }


    /**
     * Тест метода save для сохранения учетной записи.
     */
    @Test
    void saveAccountTest() {

        mutationAccountService.save(account);

        verify(passwordEncoder, times(1)).encode("password");
        verify(accountRepository, times(1)).save(account);

        verify(logger, times(1)).info("Account saved: {}", account.getEmail());
    }

    /**
     * Тест метода edit для изменения учетной записи.
     */
    @Test
    void editAccountTest() {
        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));

        mutationAccountService.edit(account.getAccountId(), accountDto);

        verify(accountRepository, times(1)).findById(account.getAccountId());
        verify(accountRepository, times(1)).save(any(Account.class));

        verify(logger, times(1)).info("Account edited: {}", "update@mail.ru");
    }


    /**
     * Тест метода delete для удаления учетной записи.
     */
    @Test
    void deleteAccountTest() {
        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));

        mutationAccountService.delete(account.getAccountId());

        verify(accountRepository, times(1)).findById(account.getAccountId());
        verify(accountRepository, times(1)).delete(account);

        verify(logger, times(1)).info("Account deleted: {}", account.getAccountId());
    }

}
