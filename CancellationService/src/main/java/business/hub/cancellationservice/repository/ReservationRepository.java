package business.hub.cancellationservice.repository;

import business.hub.cancellationservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Override
    Optional<Reservation> findById(Integer id);
}
