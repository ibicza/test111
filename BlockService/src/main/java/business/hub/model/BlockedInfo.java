package business.hub.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Модель для BlockedInfo в бд.
 */
@Entity
@Getter
@Setter
@Table(name = "blockedInfo")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BlockedInfo {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "accountId")
    private Long accountId;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "reason")
    private String reason;
}
