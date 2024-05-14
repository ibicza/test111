package business.hub.cancellationservice.service;

import business.hub.cancellationservice.model.Reservation;
import business.hub.cancellationservice.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CancellationServiceImp implements CancellationService {
    private ReservationRepository repository;

    @Autowired
    public CancellationServiceImp(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    public void cancelReservation(int id) {
        Reservation cancelReservation = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
        cancelReservation.setStatus("Not booked");
        cancelReservation.setCreationTime(LocalDateTime.now());
        repository.save(cancelReservation);
    }
}
