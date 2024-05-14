package business.hub.repository;

import business.hub.entitys.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository для сущьности Account.
 *
 * @author Ostrovsky
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    /**
     * Находит Account по email.
     *
     * @param email адресс электронной почты для осуществления поиска
     * @return найденный Account или null, если Account не найден
     */
    Account findByEmail(String email);
}
