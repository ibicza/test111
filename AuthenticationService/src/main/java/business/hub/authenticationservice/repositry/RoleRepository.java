package business.hub.authenticationservice.repositry;

import business.hub.authenticationservice.dto.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Репозиторий для работы с сущностью Role в базе данных.
 */
public interface RoleRepository extends JpaRepository<Role, Integer> {

}
