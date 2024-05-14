package business.hub.ticketingservice.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import lombok.Data;

@Entity
@Table(name = "passenger")
@Data
public class Passenger {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "passport_info_id")
    private PassportInfo passportInfo;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_info_id")
    private ContactInfo contactInfo;
}
