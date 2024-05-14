package business.hub.accountinfoservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Модель Account.
 * <p>
 * Этот класс представляет собой Entity аккаунта.
 * </p>
 *d
 * <p>
 * Поля:
 * <ul>
 *     <li>{@code passport} - личный паспорт человека</li>
 *     <li>{@code id} - Уникальный идентификатор аккаунта</li>
 *     <li>{@code email} - Уникальная электронная почта</li>
 * </ul>
 * </p>
 *
 * <p>
 * Имя таблицы: {@code accounts}
 * </p>
 *
 * @version 1.0
 * @since 2024-04-23
 */
@Entity
@Table(name = "accounts")
@Data
public class Account {
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport", referencedColumnName = "passport_id")
    private Passport passport;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(nullable = false, unique = true, name = "email")
    private String email;
}
