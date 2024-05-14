package business.hub.services.account;

import business.hub.dto.AccountDto;
import business.hub.entitys.Account;

/**
 * Интерфейс для предоставления изменений с сущностью Account.
 * @author Ostrovsky
 */

public interface MutationAccountService {
    /**
     * @param account сущность для сохранения
     */
    void save(Account account);

    /**
     * @param id для поиска сущьности в базе данных
     * @param dto для редактирования полей сущности
     */

    void edit(Long id, AccountDto dto);

    /**
     * @param id для поиска и удаления сущности
     */
    void delete(Long id);
}
