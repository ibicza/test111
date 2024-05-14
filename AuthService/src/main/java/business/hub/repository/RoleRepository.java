package business.hub.repository;

import business.hub.entitys.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JpaRepository для сущности Role.
 *
 * @author Ostrovsky
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    /**
     * @param name имя Role для поиска
     * @return Найденную Role или null, если Role не найден
     */
    Role findByName(String name);
}
