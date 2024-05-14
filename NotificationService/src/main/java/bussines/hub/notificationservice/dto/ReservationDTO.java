package bussines.hub.notificationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReservationDTO {

    private String flightNumber;
    private LocalDate bookingDate;
    private LocalDateTime creationTime;
    private String firstname;
    private String lastname;
    private String email;
}
