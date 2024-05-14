package business.hub.service.account;

import business.hub.entitys.Account;
import business.hub.repository.AccountRepository;
import business.hub.services.account.MutationAccountServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * Юнит-тест для класса ReadAccountServiceImpl,
 * отвечающего за чтение учетных записей.
 */
@ExtendWith(MockitoExtension.class)
class ReadAccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private Logger logger;
    @InjectMocks
    private MutationAccountServiceImpl mutationAccountService;

    private Account account;
    private Account secondAccount;

    /**
     * Настройка перед каждым тестом.
     */
    @BeforeEach
    void setUp() {
        account = new Account();
        account.setEmail("firstAccount@mail.ru");
        account.setPassword("firstPassword");
        account.setRegistrationDate(LocalDateTime.now());

        secondAccount = new Account();
        account.setEmail("secondAccount@mail.ru");
        account.setPassword("secondPassword");
        account.setRegistrationDate(LocalDateTime.now());
    }

    /**
     * Тест метода getAllAccounts для получения всех учетных записей.
     */
    @Test
    void getAllAccountsTest() {
        List<Account> accounts = Arrays.asList(account, secondAccount);

        when(accountRepository.findAll()).thenReturn(accounts);

        List<Account> retrievedAccount = accountRepository.findAll();

        assertEquals(2, retrievedAccount.size());
        verify(accountRepository, times(1)).findAll();
    }


    /**
     * Тест метода getAccountById для получения учетной записи по id.
     */
    @Test
    void getAccountById() {
        when(accountRepository.findById(account.getAccountId())).thenReturn(Optional.of(account));

        accountRepository.findById(account.getAccountId());

        verify(accountRepository, times(1)).findById(account.getAccountId());
    }

    /**
     * Тест метода getAccountByEmail для получения учетной записи по электронной почте.
     */
    @Test
    void getAccountByEmail() {
        when(accountRepository.findByEmail(account.getEmail())).thenReturn(account);

        accountRepository.findByEmail(account.getEmail());

        verify(accountRepository, times(1)).findByEmail(account.getEmail());
    }
}
