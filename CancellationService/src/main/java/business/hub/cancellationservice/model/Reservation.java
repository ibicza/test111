package business.hub.cancellationservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
    private int id;
    @Column(name = "service_name")
    private String serviceName;
    @Column(name = "booking_date")
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDate bookingDate;
    @Column(name = "creation_time")
    private LocalDateTime creationTime;
    @Column(name = "bookers_name")
    private String bookersName;
    @Column(name = "status")
    private String status;
}
