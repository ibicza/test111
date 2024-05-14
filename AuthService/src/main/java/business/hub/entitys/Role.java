package business.hub.entitys;

import business.hub.validation.annotation.Unique;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

/**
 * Класс Role представляет собой сущьность с индентификатором в виде ID.
 * и строки name
 * @author Ostrovsky
 */
@Data
@Entity
@NoArgsConstructor
public class Role {

    @Id
    @Column(name = "role_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @Column(name = "name", unique = true)
    @NotBlank(message = "Role is required")
    @Unique(entity = "Role", fieldName = "name", message = "Role is occupied")
    private String name;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private Set<Account> accounts = new HashSet<>();
}
