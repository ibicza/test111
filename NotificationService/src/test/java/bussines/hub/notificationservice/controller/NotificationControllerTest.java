//package bussines.hub.notificationservice.controller;
//
//import bussines.hub.notificationservice.dto.ReservationDTO;
//import bussines.hub.notificationservice.service.NotificationServiceImp;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
//
//import java.util.Arrays;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//class NotificationControllerTest {
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private NotificationServiceImp notificationService;
//
//    @MockBean
//    private KafkaTemplate<String, ReservationDTO> kafkaTemplate;
//
//    @Test
//    void changeFlight_Success() throws Exception {
//
//        String flightNumber = "1";
//        ReservationDTO reservation1 = new ReservationDTO();
//        ReservationDTO reservation2 = new ReservationDTO();
//        List<ReservationDTO> reservations = Arrays.asList(reservation1, reservation2);
//
//        when(notificationService.getFlightNumberReservations(flightNumber)).thenReturn(reservations);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/notifications/change/" + flightNumber)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        verify(kafkaTemplate, times(reservations.size())).send(eq("email-change"), any(ReservationDTO.class));
//    }
//
//    @Test
//    void cancelledFlight_Success() throws Exception {
//
//        String flightNumber = "1";
//        ReservationDTO reservation1 = new ReservationDTO();
//        ReservationDTO reservation2 = new ReservationDTO();
//        List<ReservationDTO> reservations = Arrays.asList(reservation1, reservation2);
//
//        when(notificationService.getFlightNumberReservations(flightNumber)).thenReturn(reservations);
//
//        mockMvc.perform(MockMvcRequestBuilders.post("/notifications/cancelled/" + flightNumber)
//                        .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        verify(kafkaTemplate, times(reservations.size())).send(eq("email-cancelled"), any(ReservationDTO.class));
//    }
//}
