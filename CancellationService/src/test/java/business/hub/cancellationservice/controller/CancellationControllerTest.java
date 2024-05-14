package business.hub.cancellationservice.controller;

import business.hub.cancellationservice.model.Reservation;
import business.hub.cancellationservice.service.CancellationService;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CancellationControllerTest {

    @Test
    void cancelReservation() {
        Reservation reservation = new Reservation();
        reservation.setId(1);

        CancellationService service = mock(CancellationService.class);

        CancellationController controller = new CancellationController(service);

        ResponseEntity<HttpStatus> cancelReservation = controller.cancelReservation(1);

        assertEquals(HttpStatus.OK, cancelReservation.getStatusCode());

        verify(service, times(1)).cancelReservation(1);
    }
}