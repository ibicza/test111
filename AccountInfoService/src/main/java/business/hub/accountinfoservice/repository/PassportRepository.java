package business.hub.accountinfoservice.repository;

import business.hub.accountinfoservice.model.Passport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Репозиторий для доступа к данным паспортов.
 */
@Repository
public interface PassportRepository extends JpaRepository<Passport, Long> {
    /**
     * Сохраняет паспорт в репозитории.
     *
     * @param passport паспорт для сохранения.
     * @return сохраненный паспорт.
     */
    Passport save(Passport passport);
    /**
     * Возвращает паспорт по его уникальному идентификатору.
     *
     * @param id уникальный идентификатор паспорта.
     * @return паспорт, если найден, или null, если не найден.
     */
    Passport findById(int id);
    /**
     * Возвращает список всех паспортов.
     *
     * @return список всех паспортов.
     */
    List<Passport> findAll();
}
