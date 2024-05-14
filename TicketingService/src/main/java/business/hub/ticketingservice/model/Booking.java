package business.hub.ticketingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "booking")
@Data
public class Booking {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
