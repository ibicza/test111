package business.hub.services.role;

import business.hub.dto.RoleDto;
import business.hub.entitys.Role;
import business.hub.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Ostrovsky
 * Клас для реализации методав save, edit, delete интерфеса MutationRoleService
 */
@Service
@Transactional
public class MutationRoleServiceImpl implements MutationRoleService {
    /**
     * ОБъявляем переменные roleRepository, Logger.
     */
    private final RoleRepository roleRepository;
    private Logger logger = LoggerFactory.getLogger(MutationRoleServiceImpl.class);
    /**
     * Внедряем зависимость через конструктор.
     * и добавляем логер для тестирования
     * @param roleRepositoryParam переменная зависимости
     */
    @Autowired
    public MutationRoleServiceImpl(final RoleRepository roleRepositoryParam) {
        this.roleRepository = roleRepositoryParam;
    }

    /**
     * Метод для установки мокированного logger в tests.
     * @param mockLogger logger
     */
    public void setLogger(final Logger mockLogger) {
        this.logger = mockLogger;
    }
    /**
     * Метод сохранения Role.
     *
     * @param role Role Entity
     */
    @Override
    public void save(final Role role) {

        roleRepository.save(role);

        logger.info("Role saved: {}", role.getName());
    }

    /**
     * метод редактирования Role.
     *
     * @param id      Получает параметр ID Cущности Role для извлечения из базы данных
     * @param roleDto Объект для передачи данных от клиента в базу данных
     */
    @Override
    public void edit(final Long id, final RoleDto roleDto) {
        /*
          Получем Role по ID из базы данных. Если не находим выбрасываем исключение "Role not found"
         */
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        /*
         * Устанавливаем полученные значения полей от клиента
         */
        existingRole.setName(roleDto.getName());
        /*
         * Сохраняем изменения
         */
        roleRepository.save(existingRole);
        /*
         * Произвадим логгирование
         */
        logger.info("Role edited: {}", existingRole.getName());
    }

    /**
     * Метод удаления Role.
     * @param id Получает параметр ID сущности Role для извлечения из базы данных
     */
    @Override
    public void delete(final Long id) {
        /*
         * Получем Role по ID из базы данных. Если не находим выбрасываем исключение "Role not found"
         */
        Role existingRole = roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
        /*
         * Производим удаление полученной сущности
         */
        roleRepository.delete(existingRole);
        /*
         * производим логгирование
         */
        logger.info("Role deleted: {}", existingRole.getRoleId());
    }
}
