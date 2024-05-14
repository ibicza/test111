package business.hub.repositories;

import business.hub.model.BlockedInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Класс Repository для CRUD+ операций для
 * заблокированных пользователей.
 */
@Repository
public interface BlockedInfoRepository extends JpaRepository<BlockedInfo, Long> {

    /**
     * Метод для поиска Аккаунта по id.
     * @param id
     * @return ?
     */
    BlockedInfo findByAccountId(Long id);
}
