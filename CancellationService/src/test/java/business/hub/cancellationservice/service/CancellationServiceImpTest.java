package business.hub.cancellationservice.service;

import business.hub.cancellationservice.model.Reservation;
import business.hub.cancellationservice.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CancellationServiceImpTest {

    @Mock
    private ReservationRepository repository;

    @InjectMocks
    private CancellationServiceImp service;

    @Test
    void cancelReservation_Succes() {
        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setBookingDate(LocalDate.now().plusDays(1));

        Mockito.when(repository.findById(1)).thenReturn(Optional.of(reservation));
        Mockito.when(repository.save(reservation)).thenReturn(reservation);

        service.cancelReservation(1);
        assertEquals("Not booked", reservation.getStatus());
        assertNotNull(reservation.getCreationTime());

        verify(repository, times(1)).findById(1);
        verify(repository, times(1)).save(reservation);
    }

    @Test
    void cancelReservation_EntityNotFoundException() {
        Mockito.when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            repository.findById(1).orElseThrow(EntityNotFoundException::new);
        });

        verify(repository, times(1)).findById(1);
    }
}