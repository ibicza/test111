package business.hub.userservice.controller;

import business.hub.userservice.model.User;
import business.hub.userservice.repositories.RoleRepositories;
import business.hub.userservice.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST-контроллер для управления пользователями.
 * Обрабатывает HTTP-запросы, связанные с созданием, получением, обновлением и удалением пользователей.
 */
@RestController
@RequestMapping("/users")
public class UserControllerRest {

    private final UserServices userServices;
    private final RoleRepositories roleRepositories;

    /**
     * Конструктор контроллера с инъекцией зависимостей.
     *
     * @param userServicesParam   Сервис пользователей.
     * @param roleRepositoriesParam Репозиторий ролей.
     */
    @Autowired
    public UserControllerRest(final UserServices userServicesParam,
                              final RoleRepositories roleRepositoriesParam) {
        this.userServices = userServicesParam;
        this.roleRepositories = roleRepositoriesParam;
    }


    /**
     * Создание нового пользователя.
     *
     * @param user Данные нового пользователя.
     * @return Сохраненный пользователь.
     */
    @PostMapping()
    public User saveUser(final @RequestBody User user) {

        userServices.saveUser(user);
        return user;
    }


    /**
     * Получение всех пользователей.
     *
     * @return Список всех пользователей.
     */
    @GetMapping()
    public List<User> getUsers() {
        return userServices.getAllUsers();
    }

    /**
     * Получение пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь с указанным идентификатором.
     */
    @GetMapping("/{id}")
    public User getUser(final @PathVariable("id") int id) {
        return userServices.getUser(id);
    }

    /**
     * Обновление информации о пользователе.
     *
     * @param user Обновленные данные о пользователе.
     * @return Обновленный пользователь.
     */
    @PutMapping()
    public User putUser(final @RequestBody User user) {
        userServices.saveUser(user);
        return user;
    }

    /**
     * Удаление пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя для удаления.
     * @return Сообщение об успешном удалении пользователя.
     */
    @DeleteMapping("/{id}")
    public String delUser(final @PathVariable("id") int id) {
        userServices.deleteUser(id);
        return "delete user " + id;
    }
}


