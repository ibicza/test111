package business.hub.services.role;

import business.hub.entitys.Role;
import business.hub.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Класс реализации интерфейса ReadRoleService.
 * Методы: getAllRoles(); getRoleByName(); getRoleById();
 */
@Service
@Transactional
public class ReadRoleServiceImpl implements ReadRoleService {
    /**
     * ОБъявляем переменные roleRepository, Logger.
     */
    private RoleRepository roleRepository;
    private final Logger logger = LoggerFactory.getLogger(ReadRoleServiceImpl.class);

    /**
     * Внедряем зависимость через конструктор.
     * @param roleRepositoryParam внедрённая зависимость
     */
    @Autowired
    public ReadRoleServiceImpl(final RoleRepository roleRepositoryParam) {
        this.roleRepository = roleRepositoryParam;
    }

    /**
     * Метод получения списка Roles.
     * @return список roles
     */
    @Override
    public List<Role> getAllRoles() {

        List<Role> roles = roleRepository.findAll();

        logger.info("Retrieved all roles. Count: {}", roles.size());

        return roles;
    }

    /**
     * Метод получения Role по имени.
     * @param name получает Имя Role
     * @return возвращаем Role
     */
    @Override
    public Role getRoleByName(final String name) {

        Role existingRole = roleRepository.findByName(name);

        if (existingRole == null) {
           logger.warn("Role not found by Name: {}", name);
           throw new IllegalArgumentException("Role not found");
        }
        logger.info("Role found by name: {}", existingRole.getName());
        return existingRole;
    }

    /**
     * Метод для получения Role по ID.
     * @param roleId получает ID Role
     * @return возвращает Role
     */
    @Override
    public Role getRoleById(final Long roleId) {
        Role existingRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));

        logger.info("Role found by ID: {}", existingRole.getRoleId());

        return existingRole;
    }
}
