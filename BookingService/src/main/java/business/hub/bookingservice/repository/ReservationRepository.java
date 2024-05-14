package business.hub.bookingservice.repository;

import business.hub.bookingservice.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findById(int id);

    Optional<Reservation> findByBookingDate(LocalDate data);

    Optional<List<Reservation>> findAllByFlightNumber(String num);

    List<Reservation> findByFlightNumber(String num);

}
