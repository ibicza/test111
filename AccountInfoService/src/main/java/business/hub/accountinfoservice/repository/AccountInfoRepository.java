package business.hub.accountinfoservice.repository;

import business.hub.accountinfoservice.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountInfoRepository extends JpaRepository<Account, Long> {
    /**
     * Отдает аккаунт по его ID.
     *
     * @param id Уникальный идентификатор аккаунта(ID)).
     * @return {@link Optional}, содержащий аккаунт,
     * или Optional.sPresent() = false. Не возвращает null.
     */
    Optional<Account> findById(Long id);


    /**
     * Удаляет указанный аккаунт из репозитория.
     *
     * @param account аккаунт для удаления.
     */
    void delete(Account account);
}
