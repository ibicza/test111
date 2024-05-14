package business.hub.bookingservice.controller;

import business.hub.bookingservice.model.Reservation;
import business.hub.bookingservice.service.ReservationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


class ReservationControllerTest {

    @Test
    public void testGetAllReservations_Success() {

        List<Reservation> reservations = new ArrayList<>();
        reservations.add(new Reservation());
        reservations.add(new Reservation());

        ReservationService reservationService = mock(ReservationService.class);
        when(reservationService.getAllReservation()).thenReturn(reservations);

        ReservationController controller = new ReservationController(reservationService);

        ResponseEntity<List<Reservation>> response = controller.getAllReservations();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reservations, response.getBody());

        verify(reservationService, times(1)).getAllReservation();
    }

    @Test
    public void testGetReservation_Success() {

        Reservation reservation = new Reservation();
        reservation.setId(1);

        ReservationService reservationService = mock(ReservationService.class);
        when(reservationService.getReservation(1)).thenReturn(reservation);

        ReservationController controller = new ReservationController(reservationService);


        ResponseEntity<Reservation> response = controller.getReservation(1);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertEquals(reservation, response.getBody());

        verify(reservationService, times(1)).getReservation(1);
    }

    @Test
    public void testCreateReservation_Success() {

        Reservation reservation = new Reservation();
        reservation.setId(1);

        ReservationService reservationService = mock(ReservationService.class);
        when(reservationService.createReservation(any())).thenReturn(reservation);

        ReservationController controller = new ReservationController(reservationService);

        ResponseEntity<?> response = controller.createReservation(reservation, mock(BindingResult.class));

        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        assertEquals(reservation, response.getBody());

        verify(reservationService, times(1)).createReservation(reservation);
    }

    @Test
    public void testCreateReservation_WithValidationErrors() {

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.hasErrors()).thenReturn(true);
        when(bindingResult.getAllErrors()).thenReturn(Collections.singletonList(new ObjectError("test", "Test error")));

        ReservationController controller = new ReservationController(mock(ReservationService.class));


        ResponseEntity<?> response = controller.createReservation(new Reservation(), bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        assertTrue(response.getBody() instanceof Iterable);
    }
}