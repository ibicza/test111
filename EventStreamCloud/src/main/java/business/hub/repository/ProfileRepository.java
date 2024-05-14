    package business.hub.repository;

    import business.hub.entity.ProfileEvent;
    import org.springframework.data.jpa.repository.JpaRepository;
    import org.springframework.stereotype.Repository;

    import java.util.Optional;

    @Repository
    public interface ProfileRepository extends JpaRepository<ProfileEvent, String> {
        Optional<ProfileEvent> findByAccountId (String accountId);
    }
