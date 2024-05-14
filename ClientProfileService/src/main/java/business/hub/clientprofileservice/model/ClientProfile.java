package business.hub.clientprofileservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * Сущность, представляющая профиль клиента.
 */
@Entity
@Table(name = "client_profile")
@Component
@Data
public class ClientProfile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    /**
     * Пустой конструктор для JPA.
     */
    public ClientProfile() {
    }

    /**
     * Конструктор с параметрами для создания профиля клиента.
     * @param firstNameParam Имя клиента.
     * @param lastNameParam Фамилия клиента.
     * @param emailParam Электронная почта клиента.
     */
    public ClientProfile(final String firstNameParam,
                         final String lastNameParam,
                         final String emailParam) {
        this.firstName = firstNameParam;
        this.lastName = lastNameParam;
        this.email = emailParam;
    }
}
