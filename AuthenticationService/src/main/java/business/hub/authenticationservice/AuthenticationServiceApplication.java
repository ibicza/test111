package business.hub.authenticationservice;

import business.hub.authenticationservice.dto.Role;
import business.hub.authenticationservice.dto.UserDTO;
import business.hub.authenticationservice.service.RegistrationService;
import business.hub.authenticationservice.service.RoleService;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import java.util.HashSet;
import java.util.Set;

/**
 * Главный класс приложения аутентификации.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableMethodSecurity
public class AuthenticationServiceApplication {

    /**
     * Внедрение registrationService.
     */
    private final RegistrationService registrationService;

    /**
     * Внедрение roleService.
     */
    private final RoleService roleService;

    /**
     * Конструктор главного класса приложения.
     *
     * @param registrationServiceParam сервис регистрации пользователей
     * @param roleServiceParam         сервис работы с ролями
     */
    @SuppressWarnings({"checkstyle:HiddenField", "checkstyle:LineLength"})
    public AuthenticationServiceApplication(
            final RegistrationService registrationServiceParam,
            final RoleService roleServiceParam) {
        this.registrationService = registrationServiceParam;
        this.roleService = roleServiceParam;
    }

    /**
     * Главный метод для запуска приложения.
     *
     * @param args аргументы командной строки
     */
    public static void main(final String[] args) {
        SpringApplication.run(AuthenticationServiceApplication.class, args);
    }

    /**
     * Инициализация тестовых пользователей и ролей при старте приложения.
     */
//mock users creation for testing
    @PostConstruct
    public void init() {
        Set<Role> roleAdmin = new HashSet<>();
        Set<Role> roleUser = new HashSet<>();
        Role admin = new Role("ROLE_ADMIN");
        Role user = new Role("ROLE_USER");
        roleService.saveRole(admin);
        roleService.saveRole(user);

        roleAdmin.add(admin);
        roleAdmin.add(user);
        roleUser.add(user);
        registrationService.registerUser(new UserDTO("admin",
                "pass", "email@mail.com", roleAdmin));
        registrationService.registerUser(new UserDTO("user",
                "pass", "emailTwo@gmail.com", roleUser));
    }
}
