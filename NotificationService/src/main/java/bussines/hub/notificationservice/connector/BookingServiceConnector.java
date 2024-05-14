package bussines.hub.notificationservice.connector;

import bussines.hub.notificationservice.dto.ReservationDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "BOOKING-SERVICE")
public interface BookingServiceConnector {

    @GetMapping("/reservations/flight/{number}")
    List<ReservationDTO> getFlightNumberReservations(@PathVariable("number") String num);
}
