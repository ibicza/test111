package business.hub.dto;

import business.hub.entitys.Role;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * @author Ostrovsky
 * Класс передачи данных для редактирования
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {

    private Long accountId;

    @Column(name = "email", unique = true)
    @Email(message = "Incorrect format email")
    @NotBlank(message = "Email is required")
    private String email;
    //bcrypt hash
    @Column(name = "password")
    @NotBlank(message = "The field cannot be empty")
    private String password;

    @Column(name = "registration")
    private LocalDateTime registrationDate;

    private Set<Role> roles;

}
