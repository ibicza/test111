package business.hub.authenticationservice;

import business.hub.authenticationservice.dto.Role;
import business.hub.authenticationservice.dto.UserDTO;
import business.hub.authenticationservice.repositry.RoleRepository;
import business.hub.authenticationservice.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Класс для тестирования {@link RoleService}.
 * Использует {@link RoleRepository} для мокирования репозитория.
 *
 * Класс использует библиотеку Mockito для создания мок-объектов и
 * проверки вызовов методов репозитория.
 *
 *
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = RoleService.class)
public class RoleServiceTest {

    @MockBean
    private RoleRepository roleRepository;

    @Autowired
    private RoleService roleService;

    private static final String DEFAULT_ROLE_NAME = "ROLE_USER";

    private Role role;

    /**
     * Перед каждым тестовым методом инициализирует необходимые объекты.
     * Создает моки и устанавливает начальные значения для объекта роли.
     */
    @BeforeEach
    void setUp() {
        role = new Role();

        role.setId(1);
        role.setRole(DEFAULT_ROLE_NAME);
        role.setUsers(List.of(new UserDTO()));

    }


    /**
     * Тест для метода {@link RoleService#saveRole(Role)}.
     * Проверяет сохранение роли в репозиторий.
     */

    @Test
    void saveRoleTest() {
        Mockito.when(roleRepository.save(any(Role.class))).thenReturn(role);
        roleService.saveRole(role);
        verify(roleRepository, times(1)).save(role);
    }


    /**
     * Тест для метода {@link RoleService#getDefaultRole()}.
     * Проверяет получение стандартной роли из репозитория.
     */
    @Test
    void testGetDefaultRole() {

        Role newRole = new Role();
        newRole.setId(2);
        newRole.setRole(DEFAULT_ROLE_NAME);
        newRole.setUsers(List.of(new UserDTO()));

        when(roleRepository.findAll()).thenReturn(List.of(role, newRole));

        Set<Role> defaultRoles = roleService.getDefaultRole();

        assertEquals(2, defaultRoles.size());
        assertEquals(DEFAULT_ROLE_NAME, defaultRoles.iterator().next().getAuthority());

    }

    /**
     * Тест для метода {@link RoleService#getDefaultRole()} в отрицательном сценарии.
     * Проверяет поведение метода при отсутствии стандартной роли в репозитории.
     */
    @Test
    void getDefaultRoleTestNegativeScenarioTest() {
        when(roleRepository.findById(1)).thenReturn(Optional.empty());
        Optional<Role> res = roleRepository.findById(1);
        assertFalse(res.isPresent(), "Expected Optional to be empty, but it was not");
    }


}
