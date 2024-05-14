package business.hub.userservice;

import business.hub.userservice.model.Role;
import business.hub.userservice.model.User;
import business.hub.userservice.repositories.RoleRepositories;
import business.hub.userservice.repositories.UserRepositories;
import business.hub.userservice.service.UserServices;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import java.time.LocalDate;

/**
 * Класс UserServiceApplication представляет основной
 * класс приложения для пользовательского сервиса.
 * Класс также содержит методы для инициализации тестовых данных при запуске приложения.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class UserServiceApplication {


    private final UserServices userServices;

    private final RoleRepositories roleRepositories;
    private final UserRepositories userRepositories;

    /**
     * Конструктор класса UserServiceApplication.
     * Инициализирует сервисы пользователей и репозитории ролей и пользователей.
     *
     * @param userServicesParam       Сервис для работы с пользователями.
     * @param roleRepositoriesParam   Репозиторий для доступа к данным ролей.
     * @param userRepositoriesParam   Репозиторий для доступа к данным пользователей.
     */
    @Autowired
    public UserServiceApplication(final UserServices userServicesParam,
                                  final RoleRepositories roleRepositoriesParam,
                                  final UserRepositories userRepositoriesParam) {
        this.userServices = userServicesParam;
        this.roleRepositories = roleRepositoriesParam;
        this.userRepositories = userRepositoriesParam;
    }

    /**
     * Основной метод, запускающий приложение.
     *
     * @param args Аргументы командной строки.
     */
    public static void main(final String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

    /**
     * Метод инициализации тестовых данных при запуске приложения.
     */
    @PostConstruct
    public void init() {
        try {
            if (roleRepositories.findAll().isEmpty()) {
                roleRepositories.save(new Role(1, "ROLE_USER"));
                roleRepositories.save(new Role(2, "ROLE_ADMIN"));
            }

            if (userRepositories.findAll().isEmpty()) {
                User user = new User("admin", "admin",
                        "admin", "admin",
                        "hdnhdxv@mail.ru",
                        LocalDate.parse("1970-01-01"), "CREATED");

                User user2 = new User("user", "user",
                        "user", "user",
                        "hdnhdxv@mail.ru",
                        LocalDate.parse("01.01.1970"), "CREATED");
                user.addRoleUser(roleRepositories.findById(1).orElse(null));
                user.addRoleUser(roleRepositories.findById(2).orElse(null));
                user2.addRoleUser(roleRepositories.findById(1).orElse(null));
                userServices.saveUser(user);
                userServices.saveUser(user2);
                System.out.println("Add test user" + user.toString());
                System.out.println("Add test user" + user2.toString());
            }
        } catch (Exception e) {
            System.out.println("?????????");
            System.out.println(e.getMessage());
        }
    }
}
