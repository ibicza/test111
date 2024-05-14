package business.hub.userservice.service;

import business.hub.userservice.model.User;

import java.util.List;

/**
 * Интерфейс UserServices представляет сервис для работы с пользователями.
 * Определяет методы для выполнения операций CRUD (создание, чтение, обновление, удаление) с данными пользователей.
 * Также включает методы для обновления статуса профиля пользователя и обработки событий создания профиля.
 */
public interface UserServices {

    /**
     * Получение списка всех пользователей.
     *
     * @return Список всех пользователей.
     */
    List<User> getAllUsers();

    /**
     * Получение пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя.
     * @return Пользователь с указанным идентификатором или null, если пользователь не найден.
     */
    User getUser(int id);

    /**
     * Сохранение нового пользователя.
     *
     * @param user Пользователь для сохранения.
     */
    void saveUser(User user);

    /**
     * Удаление пользователя по его идентификатору.
     *
     * @param id Идентификатор пользователя для удаления.
     */
    void deleteUser(int id);

    /**
     * Обновление статуса профиля пользователя.
     *
     * @param userId Идентификатор пользователя.
     * @param status Новый статус профиля.
     */
    void updateProfileStatus(int userId, String status);

    /**
     * Обработка события создания профиля пользователя.
     *
     * @param userId  Идентификатор пользователя.
     * @param created Флаг успешного создания профиля.
     */
    void handleProfileCreationEvent(int userId, boolean created);
}

