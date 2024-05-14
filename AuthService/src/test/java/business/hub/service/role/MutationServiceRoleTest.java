package business.hub.service.role;

import business.hub.dto.RoleDto;
import business.hub.services.role.MutationRoleServiceImpl;
import business.hub.entitys.Role;
import business.hub.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит-тест для класса MutationRoleServiceImpl, отвечающего за мутации ролей.
 */
@ExtendWith(MockitoExtension.class)
class MutationRoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private Logger logger;
    @InjectMocks
    private MutationRoleServiceImpl mutationRoleService;

    /**
     * Настройка перед каждым тестом.
     */
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);
        mutationRoleService = new MutationRoleServiceImpl(roleRepository);
        mutationRoleService.setLogger(logger);

    }

    /**
     * Тест метода save для сохранения роли.
     */
    @Test
    void saveRoleTest() {
        Role role = new Role();
        role.setName("ROLE_USER");

        mutationRoleService.save(role);

        verify(roleRepository, times(1)).save(role);
        verify(logger, times(1)).info("Role saved: {}", role.getName());
    }

    /**
     * Тест метода edit для изменения роли.
     */
    @Test
    void editRoleTest() {
        Long roleId = 1L;
        Role existingRole = new Role();
        existingRole.setRoleId(roleId);
        existingRole.setName("User");

        RoleDto dto = new RoleDto();
        dto.setName("UpdatedUser");

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(existingRole));

        mutationRoleService.edit(roleId, dto);

        verify(roleRepository, times(1)).findById(roleId);
        verify(roleRepository, times(1)).save(existingRole);
        verify(logger, times(1)).info("Role edited: {}", existingRole.getName());
        assertEquals(dto.getName(), existingRole.getName());
    }

    @Test
    void deleteTest() {
        Long roleId = 1L;
        Role existingRole = new Role();
        existingRole.setRoleId(roleId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of((existingRole)));

        mutationRoleService.delete(roleId);

        verify(roleRepository, times(1)).findById(roleId);
        verify(logger, times(1)).info("Role deleted: {}", existingRole.getRoleId());
    }

}
