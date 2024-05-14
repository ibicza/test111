package business.hub.bookingservice.service;

import business.hub.bookingservice.dto.ReservationDTO;
import business.hub.bookingservice.model.Reservation;
import business.hub.bookingservice.repository.ReservationRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReservationServiceImp implements ReservationService {

    private ReservationRepository repository;

    @Autowired
    public ReservationServiceImp(ReservationRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservation() {
        return repository.findAll();
    }

    @Override
    public Reservation getReservation(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Reservation not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ReservationDTO> getFlightNumberReservation(String num) {
        List<ReservationDTO> reservationDTOS = repository.findAllByFlightNumber(num)
                .orElseThrow(() -> new EntityNotFoundException("Flight not booked"))
                .stream()
                .filter(reservation -> "Booked".equals(reservation.getStatus()))
                .sorted(Comparator.comparing(Reservation::getStatus))
                .map(reservation -> {
                    ReservationDTO dto = new ReservationDTO();
                    dto.setFlightNumber(reservation.getFlightNumber());
                    dto.setBookingDate(reservation.getBookingDate());
                    dto.setCreationTime(reservation.getCreationTime());
                    dto.setFirstname(reservation.getFirstname());
                    dto.setLastname(reservation.getLastname());
                    dto.setEmail(reservation.getEmail());
                    return dto;
                })
                .collect(Collectors.toList());
        return reservationDTOS;
    }


    @Override
    @Transactional
    public Reservation createReservation(Reservation res) {
        if (repository.findByBookingDate(res.getBookingDate()).isPresent()) {
            throw new EntityExistsException("This time is already booked");
        }
        res.setStatus("Booked");
        res.setCreationTime(LocalDateTime.now());
        return repository.save(res);
    }
}
