package business.hub.authenticationservice.dto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

/**
 * Класс, представляющий таблицу users в системе, связанную с
 * {@link Role} отношением Многие ко Многим.
 * Публичные поля: username,password,password
 */

@Entity
@Setter
@Getter
@ToString
@AllArgsConstructor
@Transactional
@NoArgsConstructor
@Table(name = "users")
public class UserDTO implements UserDetails {
    /**
     * Уникальный идентификатор пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    /**
     * Имя пользователя.
     */
    @SuppressWarnings("checkstyle:MagicNumber")
    @Column(name = "username", length = 25)
    @NotEmpty(message = "Username cannot be empty")
    @Size(min = 4, max = 25, message = "Username must be 4 to 25 characters long")
    private String username;
    /**
     * Пароль пользователя.
     */
    @Column(name = "password", length = 65)
    @Size(min = 4, max = 65, message = "Password must be 4 to 65 characters long")
    @NotEmpty(message = "Password cannot be empty")
    private String password;
    /**
     * Электронная почта пользователя.
     */
    @Column(name = "email", length = 65)
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Enter valid email")
    private String email;

    /**
     * Токен доступа пользователя.
     */
    @Column(name = "access_token")
    private String accessToken;
    /**
     * Токен обновления пользователя.
     */
    @Column(name = "refresh_token")
    private String refreshToken;

    /**
     * Список ролей, связанных с пользователем.
     */
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    /**
     * Конструктор класса UserDTO.
     *
     * @param usernameUser имя пользователя
     * @param passwordUser пароль пользователя
     * @param emailUser электронная почта пользователя
     * @param rolesUser роли пользователя
     */
    public UserDTO(final String usernameUser,
                   final String passwordUser,
                   final String emailUser,
                   final Set<Role> rolesUser) {
        this.username = usernameUser;
        this.password = passwordUser;
        this.email = emailUser;
        this.roles = rolesUser;
    }
    /**
     * equals (id,username,email).
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDTO userDTO)) {
            return false;
        }
        return id == userDTO.id
                && Objects.equals(username, userDTO.username)
                && Objects.equals(email, userDTO.email);
    }
    /**
     * hashCode (id,username,email).
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, username, email);
    }
    /**
     * Получение коллекции ролей пользователя.
     *
     * @return set ролей пользователя
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }
    /**
     * UserDetails метод, аккаунт просрочен ?
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * UserDetails метод, аккаунт заблокирован ?
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * UserDetails метод, аккаунт не истрачен ?
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * UserDetails метод, аккаунт активен ?
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
