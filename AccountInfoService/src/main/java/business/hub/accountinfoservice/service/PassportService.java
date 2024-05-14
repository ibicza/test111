package business.hub.accountinfoservice.service;

import business.hub.accountinfoservice.model.Passport;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления паспортами.
 */
public interface PassportService {
    /**
     * Добавляет новый паспорт с указанными данными.
     *
     * @param fullName         полное имя владельца паспорта.
     * @param dateOfBirth      дата рождения владельца паспорта.
     * @param passportNumber   номер паспорта.
     * @param issuingAuthority орган, выдавший паспорт.
     * @param issueDate        дата выдачи паспорта.
     * @param expiryDate       дата истечения срока действия паспорта.
     */
    void addPassport(String fullName, LocalDateTime dateOfBirth, String passportNumber,
                     String issuingAuthority, LocalDateTime issueDate, LocalDateTime expiryDate);

    /**
     * Возвращает паспорт по его уникальному идентификатору.
     *
     * @param id уникальный идентификатор паспорта.
     * @return объект типа {@link Optional}, содержащий паспорт, если найден, или пустой, если не найден.
     */
    Optional<Passport> getPassportById(Long id);

    /**
     * Возвращает список всех паспортов.
     *
     * @return список всех паспортов.
     */
    List<Passport> getAllPassports();
}
