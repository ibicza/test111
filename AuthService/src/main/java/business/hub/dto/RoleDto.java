package business.hub.dto;

import business.hub.entitys.Account;
import business.hub.validation.annotation.Unique;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
/**
 * @author Ostrovsky
 * Класс передачи данных для редактирования
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private Long roleId;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Role is required")
    @Unique(entity = "Role", fieldName = "name", message = "Role is occupied")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Account> accounts;

}
