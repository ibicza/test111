package business.hub.accountinfoservice.service;

import business.hub.accountinfoservice.model.Passport;
import business.hub.accountinfoservice.repository.PassportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
/**
 * Реализация интерфейса {@link PassportService}.
 */
@Service
@RequiredArgsConstructor
public class PassportServiceImpl implements PassportService {


    private PassportRepository passportRepository;

    /**
     * Конструктор для passportRepositoryParam.
     * @param passportRepositoryParam - паспорт человека.
     */
    public PassportServiceImpl(final PassportRepository passportRepositoryParam) {
        this.passportRepository = passportRepositoryParam;
    }
    /**
     * Добавляет новый паспорт с указанными данными.
     *
     * @param fullNameParam         полное имя владельца паспорта.
     * @param dateOfBirthParam      дата рождения владельца паспорта.
     * @param passportNumberParam   номер паспорта.
     * @param issuingAuthorityParam орган, выдавший паспорт.
     * @param issueDateParam        дата выдачи паспорта.
     * @param expiryDateParam       дата истечения срока действия паспорта.
     */
    @Override
    public void addPassport(final String fullNameParam, final LocalDateTime dateOfBirthParam,
                            final String passportNumberParam, final String issuingAuthorityParam,
                            final LocalDateTime issueDateParam, final LocalDateTime expiryDateParam) {
        Passport passport = new Passport(fullNameParam, dateOfBirthParam, passportNumberParam,
                issuingAuthorityParam, issueDateParam, expiryDateParam);
        passportRepository.save(passport);
    }

    /**
     * Возвращает паспорт по его уникальному идентификатору.
     *
     * @param id уникальный идентификатор паспорта.
     * @return объект типа {@link Optional}, содержащий паспорт, если найден, или пустой, если не найден.
     */
    @Override
    public Optional<Passport> getPassportById(final Long id) {
        return passportRepository.findById(id);
    }

    /**
     * Возвращает список всех паспортов.
     *
     * @return список всех паспортов.
     */
    @Override
    public List<Passport> getAllPassports() {
        return passportRepository.findAll();
    }
}
