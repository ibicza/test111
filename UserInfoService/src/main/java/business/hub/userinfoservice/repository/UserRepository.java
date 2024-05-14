package business.hub.userinfoservice.repository;

import business.hub.userinfoservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

/**
 * Интерфейс репозитория для работы с пользователями.
 * Расширяет JpaRepository для выполнения операций CRUD над объектами типа User.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    /**
     * Поиск пользователей по их идентификаторам.
     *
     * @param ids Список идентификаторов пользователей.
     * @return Список пользователей с указанными идентификаторами.
     */
    List<User> findUsersByIdIn(List<Long> ids);
    /**
     * Поиск пользователей по дате рождения.
     *
     * @param today Дата для поиска пользователей.
     * @return Список пользователей, родившихся в указанную дату.
     */
    List<User> findByBirthday(LocalDate today);

}

