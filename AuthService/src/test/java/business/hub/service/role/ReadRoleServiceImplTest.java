package business.hub.service.role;


import business.hub.entitys.Role;
import business.hub.repository.RoleRepository;
import business.hub.services.role.ReadRoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит-тест для класса ReadRoleServiceImpl, отвечающего за чтение ролей.
 */
@ExtendWith({MockitoExtension.class})
class ReadRoleServiceImplTest {
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private Logger logger;
    @InjectMocks
    private ReadRoleServiceImpl readRoleService;

    /**
     * Тест метода getAllRoles для получения всех ролей.
     */
    @Test
    void getAllRolesTest() {

        Role role1 = new Role();
        Role role2 = new Role();

        List<Role> roles = Arrays.asList(role1, role2);

        when(roleRepository.findAll()).thenReturn(roles);

        List<Role> retrievedRoles = readRoleService.getAllRoles();

        assertEquals(2, retrievedRoles.size());
        verify(roleRepository, times(1)).findAll();
    }

    /**
     * Тест метода getRoleByName для получения роли по имени.
     */
    @Test
    void getRoleByName() {
        Role role = new Role();
        String roleName = "User";
        role.setName(roleName);

        when(roleRepository.findByName(roleName)).thenReturn(role);

        Role retrievedRole = readRoleService.getRoleByName(roleName);

        assertEquals(roleName, retrievedRole.getName());
        verify(roleRepository, times(1)).findByName(roleName);
    }

    /**
     * Тест метода getRoleById для получения роли по идентификатору.
     */
    @Test
    void getRoleById() {
        Long roleId = 1L;
        Role role = new Role();
        role.setRoleId(roleId);

        when(roleRepository.findById(roleId)).thenReturn(Optional.of(role));

        Role retrievedRole = readRoleService.getRoleById(roleId);

        assertEquals(roleId, retrievedRole.getRoleId());
        verify(roleRepository, times(1)).findById(roleId);
    }
}
