package business.hub.userservice.service;

import business.hub.userservice.model.Role;
import business.hub.userservice.model.User;
import business.hub.userservice.repositories.RoleRepositories;
import business.hub.userservice.repositories.UserRepositories;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Класс UserServicesImpl реализует сервис для работы с пользователями.
 * Предоставляет методы для выполнения операций CRUD с данными пользователей.
 * Также включает методы для обновления статуса профиля пользователя,
 * обработки событий создания профиля и регистрации нового пользователя.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServicesImpl implements UserServices {

    private final UserRepositories userRepositories;
    private final RoleRepositories roleRepositories;
    private final StreamBridge streamBridge;

    /**
     * Получение списка всех пользователей.
     *
     * @return Список всех пользователей.
     */
    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepositories.findAll();
        log.info("Get all users: {}", users);
        return users;
    }


    /**
     * Получение пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь с указанным идентификатором или null, если пользователь не найден.
     */
    @Override
    public User getUser(final int id) {
        Optional<User> user = userRepositories.findById(id);
        log.info("Get user: {}", user.orElse(null));
        return user.orElse(null);
    }

    /**
     * Сохранение нового пользователя.
     *
     * @param user Пользователь для сохранения.
     */
    @Override
    public void saveUser(final User user) {
        List<Role> roles = new ArrayList<>();
        user.getRoles().forEach(role -> {
            roles.add(roleRepositories.findById(role.getId()).orElse(null));
        });
        user.setRoles(roles);
        User saved = userRepositories.save(user);
        log.info("Success save user: {}", saved);
    }


    /**
     * Удаление пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя для удаления.
     */
    @Override
    public void deleteUser(final int id) {
        if (userRepositories.existsById(id)) {
            userRepositories.deleteById(id);
            log.info("Success delete by id: {}", id);
        } else {
            throw new EntityNotFoundException();
        }
    }

    /**
     * Обработка события создания профиля пользователя.
     *
     * @param userId  Идентификатор пользователя.
     * @param created Флаг успешного создания профиля.
     */
    @Override
    public void handleProfileCreationEvent(final int userId, final boolean created) {
        String status = created ? "CREATED" : "ERROR";
        // Если профиль не создан, можно использовать статус ERROR или REJECTED
        updateProfileStatus(userId, status);
    }

    /**
     * Регистрация нового пользователя.
     *
     * @param username Имя пользователя.
     * @return Сохраненный пользователь.
     */
    public User registerUser(final String username) {
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setProfileStatus("CREATING");
        User savedUser = userRepositories.save(newUser);

        // Отправляем событие на создание профиля
        streamBridge.send("profile-create-out-0", savedUser.getId());
        log.info("Событие на создание профиля отправлено для пользователя: {}",
                savedUser.getUsername());

        return savedUser;
    }

    /**
     * Обновление статуса профиля пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param status Новый статус профиля.
     */
    public void updateProfileStatus(final int userId, final String status) {
        User user = userRepositories.findById(userId).orElseThrow(()
                -> new EntityNotFoundException("Пользователь не найден: " + userId));
        user.setProfileStatus(status);
        userRepositories.save(user);
        log.info("Статус профиля пользователя {} обновлен на: {}", userId, status);
    }

}
