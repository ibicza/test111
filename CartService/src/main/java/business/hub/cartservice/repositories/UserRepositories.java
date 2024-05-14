package business.hub.cartservice.repositories;

import business.hub.cartservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepositories extends JpaRepository<User, Integer> {
    /**
     * Ищем пользователя по username.
     * @param username - имя
     * @return User
     */
    User findByUsername(String username);
}
