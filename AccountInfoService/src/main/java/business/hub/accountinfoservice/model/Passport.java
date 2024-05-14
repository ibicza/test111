package business.hub.accountinfoservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Модель Passport.
 * <p>
 * Этот класс представляет собой Entity паспорта.
 * </p>
 *d
 * <p>
 *
 * <p>
 * Имя таблицы: {@code passport}
 * </p>
 *
 */
@Entity
@Table(name = "passport")
@Getter
@Setter
@NoArgsConstructor
public class Passport {

    @Column(name = "fullName")
    private String fullName;

    @Column(name = "dateOfBirth")
    private LocalDateTime dateOfBirth;

    @Column(name = "passportNumber")
    private String passportNumber;

    @Column(name = "issuingAuthority")
    private String issuingAuthority;

    @Column(name = "issueDate")
    private LocalDateTime issueDate;

    @Column(name = "expiryDate")
    private LocalDateTime expiryDate;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "passport_id")
    private Long id;

    /**
     * Конструктор класса Passport для создания экземпляра паспорта с указанными данными.
     *
     * @param fullNameParam         полное имя владельца паспорта.
     * @param dateOfBirthParam      дата рождения владельца паспорта.
     * @param passportNumberParam   номер паспорта.
     * @param issuingAuthorityParam орган, выдавший паспорт.
     * @param issueDateParam        дата выдачи паспорта.
     * @param expiryDateParam       дата истечения срока действия паспорта.
     */
    public Passport(final String fullNameParam, final LocalDateTime dateOfBirthParam,
                    final String passportNumberParam, final String issuingAuthorityParam,
                    final LocalDateTime issueDateParam, final LocalDateTime expiryDateParam) {
        this.fullName = fullNameParam;
        this.dateOfBirth = dateOfBirthParam;
        this.passportNumber = passportNumberParam;
        this.issuingAuthority = issuingAuthorityParam;
        this.issueDate = issueDateParam;
        this.expiryDate = expiryDateParam;
    }

}
