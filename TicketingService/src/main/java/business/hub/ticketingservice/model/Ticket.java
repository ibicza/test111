package business.hub.ticketingservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket")
@Data
public class Ticket {
    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "number")
    private int number;

    @Column(name = "event_date_time")
    private LocalDateTime eventDateTime;

    @Column(name = "address")
    private String address;
}
