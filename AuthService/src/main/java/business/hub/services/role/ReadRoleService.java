package business.hub.services.role;

import business.hub.entitys.Role;

import java.util.List;

/**
 * @author Ostrovsky
 * Интерфейс для получения списка Role, получение Role по имени,
 * получение Role по ID
 */
public interface ReadRoleService {
    /**
     * Метод получения списка всех Roles.
     * @return возвращающет список Roles
     */
    List<Role> getAllRoles();

    /**
     * Метод получения Role по имени.
     * @param name получает Имя Role
     * @return возвращает Role
     */
    Role getRoleByName(String name);

    /***
     * Мтод получения Role по имени.
     * @param roleId получает ID Role
     * @return возвращает Role
     */
    Role getRoleById(Long roleId);
}
