package business.hub.services.role;

import business.hub.dto.RoleDto;
import business.hub.entitys.Role;

/**
 * @author Ostrovsky
 * Интерфейс для Etity Role. Сохранение, удаление, редактирование
 */
public interface MutationRoleService {
    /**
     * Получает параметр Role и сохраняет её.
     * @param role Role Entity
     */
    void save(Role role);

    /**
     * Метод редактирования Enity Role.
     * @param id Получает параметр ID Cущности Role для извлечения из базы данных
     * @param roleDto Объект для передачи данных от клиента в базу данных
     */
    void edit(Long id, RoleDto roleDto);

    /**
     * Метод удаления.
     * @param id Получает параметр ID сущности Role для извлечения из базы данных
     */
    void delete(Long id);
}
