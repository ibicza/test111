package business.hub.bookingservice.service;

import business.hub.bookingservice.model.Reservation;
import business.hub.bookingservice.repository.ReservationRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceImpTest {

    @Mock
    private ReservationRepository repository;

    @InjectMocks
    private ReservationServiceImp service;

    @Test
    public void testGetAllReservation_Success() {

        Reservation reservation1 = new Reservation();
        reservation1.setId(1);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2);
        List<Reservation> expectedReservations = Arrays.asList(reservation1, reservation2);

        when(repository.findAll()).thenReturn(expectedReservations);
        List<Reservation> actualReservations = service.getAllReservation();

        verify(repository).findAll();
    }

    @Test
    public void testGetAllReservation_EmptyList() {

        when(repository.findAll()).thenReturn(Collections.emptyList());

        List<Reservation> result = service.getAllReservation();
        assertEquals(Collections.emptyList(), result);

        verify(repository).findAll();
    }

    @Test
    public void testGetReservation_EntityNotFoundException() {

        when(repository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            service.getReservation(1);
        });

        verify(repository, times(1)).findById(1);
    }

    @Test
    public void testCreateReservation_Success() {

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setBookingDate(LocalDate.now().plusDays(1));


        when(repository.findByBookingDate(reservation.getBookingDate())).thenReturn(Optional.empty());

        when(repository.save(reservation)).thenReturn(reservation);

        Reservation result = service.createReservation(reservation);
        assertNotNull(result);
        assertEquals("Booked", result.getStatus());
        assertNotNull(result.getCreationTime());

        verify(repository, times(1)).findByBookingDate(reservation.getBookingDate());
        verify(repository, times(1)).save(reservation);
    }

    @Test
    public void testCreateReservation_EntityExistsException() {

        Reservation reservation = new Reservation();
        reservation.setId(1);
        reservation.setBookingDate(LocalDate.now());

        when(repository.findByBookingDate(reservation.getBookingDate())).thenReturn(Optional.of(reservation));

        assertThrows(EntityExistsException.class, () -> {
            service.createReservation(reservation);
        });

        verify(repository, times(1)).findByBookingDate(reservation.getBookingDate());
        verify(repository, never()).save(reservation);
    }
}