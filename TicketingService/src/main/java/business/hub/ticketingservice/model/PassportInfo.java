package business.hub.ticketingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "passport_info")
@Data
public class PassportInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "passport_number")
    private int passportNumber;

    @Column(name = "passport_series")
    private int passportSeries;

    @Column(name = "unit_code")
    private String unitCode;
}
