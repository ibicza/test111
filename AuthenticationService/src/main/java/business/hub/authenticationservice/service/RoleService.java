package business.hub.authenticationservice.service;

import business.hub.authenticationservice.dto.Role;
import business.hub.authenticationservice.repositry.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Сервис для работы с ролями пользователей.
 */
@Service
@Transactional
public class RoleService {

    /**
     * Репозиторий для работы с ролями.
     */
    private final RoleRepository roleRepository;

    /**
     * Множество всех ролей в системе.
     */
    private Set<Role> allRoles;

    /**
     * Имя роли по умолчанию.
     */
    private static final String DEFAULT_ROLE_NAME = "ROLE_USER";

    /**
     * Множество ролей по умолчанию.
     */
    private Set<Role> defaultRole;

    /**
     * Конструктор сервиса ролей.
     *
     * @param roleRepositoryParam репозиторий для работы с ролями
     */
    public RoleService(final RoleRepository roleRepositoryParam) {
        this.roleRepository = roleRepositoryParam;
        defaultRole = new HashSet<>();
    }
    /**
     * Сохранение роли.
     *
     * @param role роль для сохранения
     */
    public void saveRole(final Role role) {
        roleRepository.save(role);
    }

    /**
     * Получение роли по умолчанию.
     *
     * @return множество ролей по умолчанию
     */
    public Set<Role> getDefaultRole() {
        if (defaultRole.isEmpty()) {
            defaultRole = getAllRoles().stream().filter(role ->
                            role.getAuthority().equals(DEFAULT_ROLE_NAME))
                    .collect(Collectors.toSet());
        }
        return defaultRole;
    }

    /**
     * Получение всех ролей из базы данных.
     *
     * @return множество всех ролей
     */
    public Set<Role> getAllRoles() {
        if (allRoles == null) {
            allRoles = new HashSet<>(roleRepository.findAll());
        }
        return allRoles;
    }
}
