package business.hub.accountinfoservice;

import business.hub.accountinfoservice.dto.AccountDTO;
import business.hub.accountinfoservice.model.Account;
import business.hub.accountinfoservice.model.Passport;
import business.hub.accountinfoservice.repository.AccountInfoRepository;
import business.hub.accountinfoservice.service.AccountInfoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = AccountInfoServiceImpl.class)
class AccountInfoServiceApplicationTests {

    @MockBean
    private AccountInfoRepository accountInfoRepository;

    @Autowired
    private AccountInfoServiceImpl accountService;

    private  Account account;

    private  AccountDTO accountDTO;
    @BeforeEach
    void setUp() {

        accountDTO = new AccountDTO();

        accountDTO.setEmail("accDto@kek");
        accountDTO.setPassport(new Passport());


        account = new Account();
        account.setEmail("test@example.com");
    }


    @Test
    void getEmailByAccountIdAccountFoundTest() throws AccountNotFoundException {
        Long accountId = 1L;
        String email = "test@example.com";

        when(accountInfoRepository.findById(accountId)).thenReturn(Optional.of(account));

        String result = accountService.getEmailByAccountId(accountId.toString());

        assertEquals(email, result);
    }

    @Test
    void createAccountTest() {

        account.setPassport(accountDTO.getPassport());
        account.setEmail(accountDTO.getEmail());

        when(accountInfoRepository.save(any(Account.class))).thenReturn(account);

        Long res = accountService.createAccount(accountDTO);

        assertEquals(account.getId(),res);
        verify(accountInfoRepository, times(1)).save(any(Account.class));

    }

    @Test
    void getAccountByAccountIdTest () {

        when(accountInfoRepository.findById(account.getId())).thenReturn(Optional.of(account));

        accountService.getAccountByAccountId(account.getId());

        assertNotNull(account);

    }

    @Test
    void updateAccountTest () {

        account.setEmail(accountDTO.getEmail());
        account.setPassport(accountDTO.getPassport());

        when(accountInfoRepository.findById(account.getId())).thenReturn(Optional.of(account));

        accountService.updateAccount(accountDTO, account.getId());

        verify(accountInfoRepository,times(1)).findById(account.getId());
        verify(accountInfoRepository, times(1)).save(any(Account.class));

    }
    @Test
    public void testDeleteAccount_WhenAccountExists() {

        when(accountInfoRepository.findById(account.getId())).thenReturn(Optional.of(account));

        assertDoesNotThrow(() -> accountService.deleteAccount(account.getId()));

        verify(accountInfoRepository, times(1)).findById(account.getId());
        verify(accountInfoRepository, times(1)).delete(any(Account.class));
    }

}
