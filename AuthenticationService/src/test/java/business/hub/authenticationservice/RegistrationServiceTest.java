package business.hub.authenticationservice;

import business.hub.authenticationservice.dto.Role;
import business.hub.authenticationservice.dto.UserDTO;
import business.hub.authenticationservice.jwt_utils.AccessTokenUtil;
import business.hub.authenticationservice.repositry.UserRepository;
import business.hub.authenticationservice.service.RegistrationService;
import business.hub.authenticationservice.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Юнит-тесты для {@link RegistrationService}.
 * Этот класс использует Mockito для создания заглушек зависимостей и тестирования поведения службы регистрации.
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = RegistrationService.class)
class RegistrationServiceTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private RoleService roleService;

    @MockBean
    private BCryptPasswordEncoder passEncoder;

    @MockBean
    private AccessTokenUtil accessTokenUtil;


    @Autowired
    private RegistrationService registrationService;

    /**
     * Метод настройки для инициализации заглушек объектов перед каждым тестовым методом.
     * Здесь также настраивается возвращаемое значение для метода кодирования пароля.
     */
    @BeforeEach
    void setUp() {
        when(passEncoder.encode(anyString())).thenReturn("password");
    }

    /**
     * Тест регистрации пользователя.
     * Проверяет, что служба регистрации корректно обрабатывает данные пользователя,
     * кодирует пароль, устанавливает роль по умолчанию и сохраняет пользователя в репозитории.
     */
    @Test
    void registerUserTest() {

        Role role = new Role();
        role.setId(1);
        role.setRole("USER_ROLE");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername("testUser");
        userDTO.setPassword("password");
        userDTO.setEmail("test@example.com");
        userDTO.setRoles(new HashSet<>(List.of(role)));
        userDTO.setRefreshToken("refreshToken");
        userDTO.setAccessToken("accToken");

        when(roleService.getDefaultRole()).thenReturn(Set.of(new Role("ROLE_USER")));
        when(passEncoder.encode(anyString())).thenReturn("password");

        registrationService.registerUser(userDTO);

        System.out.println(userDTO);
        verify(passEncoder).encode("password");
        verify(userRepository, times(1)).save(userDTO);
    }

}
