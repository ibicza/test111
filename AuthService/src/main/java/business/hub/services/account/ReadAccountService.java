package business.hub.services.account;

import business.hub.entitys.Account;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Интерфейс получения данных Account из базы.
 * @author Ostrovsky
 */
@Service
public interface ReadAccountService {
    /**
     *
     * @return возвращает список accounts из базы.
     */
    List<Account> getAllAccount();

    /**
     *
     * @param email использует email для получения account.
     * @return возвращает account по email
     */
    Account getAccountByEmail(String email);

    /**
     *
     * @param id использует id для получения account.
     * @return возвращает account по id
     */
    Account getAccountById(Long id);

}
