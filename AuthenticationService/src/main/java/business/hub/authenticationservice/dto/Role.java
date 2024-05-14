package business.hub.authenticationservice.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Objects;

/**
 * Класс, представляющий таблицу roles в системе, связанную с
 * {@link UserDTO} отношением Многие ко Многим.
 * Есть 2 роли : ROLE_ADMIN и ROLE_USER, они указаны
 * в {@link business.hub.authenticationservice.AuthenticationServiceApplication} @PostConstruct.
 */

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "roles")
public class Role implements GrantedAuthority {

    /**
     * Уникальный идентификатор роли.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Наименование роли.
     */
    @Column(name = "role")
    private String role;

    /**
     * Список пользователей с данной ролью.
     * Поле является временным и не сохраняется в базе данных напрямую.
     */
    @Transient
    @ManyToMany(mappedBy = "roles")
    private List<UserDTO> users;

    /**
     * Конструктор класса Role.
     *
     * @param roleUser наименование роли
     */
    public Role(final String roleUser) {
        this.role = roleUser;
    }

    /**
     * Получение наименования роли в формате.
     *
     * @return наименование роли
     */
    @Override
    public String getAuthority() {
        return this.role;
    }

    /**
     * equals (id, role).
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Role roleUser = (Role) o;
        return id == roleUser.id && Objects.equals(roleUser, roleUser.role);
    }
    /**
     * hashCode (id, role, users).
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, role, users);
    }
    /**
     * toString (id, role).
     */
    @Override
    public String toString() {
        return "Role{"
                + "id=" + id
                + ", role='" + role + '\''
                + '}';
    }
}
