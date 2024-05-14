package business.hub.bookingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "flight_number")
    @NotNull
    private String flightNumber;
    @Column(name = "booking_date")
    @NotNull
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate bookingDate;
    @Column(name = "creation_time")
    private LocalDateTime creationTime;
    @Column(name = "firstname")
    @NotNull
    private String firstname;
    @Column(name = "lastname")
    @NotNull
    private String lastname;
    @Column(name = "email")
    @NotNull
    @Email
    private String email;
    @Column(name = "status")
    private String status;

}
