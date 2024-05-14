//package bussines.hub.notificationservice.service;
//
//import bussines.hub.notificationservice.connector.BookingServiceConnector;
//import bussines.hub.notificationservice.dto.ReservationDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//class NotificationServiceImpTest {
//    @Mock
//    private BookingServiceConnector connector;
//    @InjectMocks
//    private NotificationServiceImp service;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this); // Инициализация макетов
//    }
//
//    @Test
//    void getFlightNumberReservations_Success() {
//
//        ReservationDTO reservation1 = new ReservationDTO();
//        ReservationDTO reservation2 = new ReservationDTO();
//        List<ReservationDTO> expectedReservations = Arrays.asList(reservation1, reservation2);
//
//        when(connector.getFlightNumberReservations("1")).thenReturn(expectedReservations);
//
//
//        List<ReservationDTO> actualReservations = service.getFlightNumberReservations("1");
//
//        assertEquals(expectedReservations, actualReservations);
//
//        verify(connector).getFlightNumberReservations("1");
//
//    }
//}