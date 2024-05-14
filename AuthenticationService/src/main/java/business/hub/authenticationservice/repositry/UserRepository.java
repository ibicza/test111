package business.hub.authenticationservice.repositry;

import business.hub.authenticationservice.dto.UserDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Репозиторий для работы с сущностью UserDTO в базе данных.
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<UserDTO, Integer> {
    /**
     * Поиск пользователя по имени пользователя.
     *
     * @param username имя пользователя
     * @return объект Optional с найденным пользователем или null
     */
    Optional<UserDTO> findByUsername(String username);

    /**
     * Поиск пользователя по токену обновления.
     *
     * @param refreshToken токен обновления
     * @return объект Optional с найденным пользователем или null
     */
    Optional<UserDTO> findByRefreshToken(String refreshToken);
}
