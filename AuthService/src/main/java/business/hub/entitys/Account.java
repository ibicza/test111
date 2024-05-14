package business.hub.entitys;

import business.hub.validation.annotation.Unique;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
/**
 * Класс Account представляет сущностть аккаунта, реализующет поля идентификации в виде ID.
 * username в виде email, а также дату регистрации registrationDate и установленную сущность Role.
 * Так же используются аннотации для ваолидации, включая кастомную аннотацию @Unique
 * для проверки уникальности username
 * @author Ostrovsky
 */
@Data
@Entity
@NoArgsConstructor
public class Account {

    @Id
    @Column(name = "account_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accountId;

    @Column(name = "email", unique = true)
    @Email(message = "Incorrect format email")
    @Unique(entity = "account", fieldName = "email", message = "Email is occupied")
    @NotBlank(message = "Email is required")
    private String email;

    //bcrypt hash
    @Column(name = "password")
    @NotBlank(message = "The field cannot be empty")
    private String password;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "account_roles",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();
}

