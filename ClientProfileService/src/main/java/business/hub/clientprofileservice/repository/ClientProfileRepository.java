package business.hub.clientprofileservice.repository;

import business.hub.clientprofileservice.model.ClientProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Репозиторий для работы с профилями клиентов.
 */
public interface ClientProfileRepository extends JpaRepository<ClientProfile, Integer> {

    /**
     * Метод для поиска профиля клиента по его идентификатору.
     * @param id Идентификатор клиента.
     * @return Объект Optional, содержащий профиль клиента, если найден.
     */
    Optional<ClientProfile> findById(Integer id);
}
