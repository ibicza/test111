package business.hub.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@Entity
@Table(name = "bills")
public class Bill {
    /** Поле идентификатор.*/
    @Id
    @Column(name = "bill_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long billId;

    /** Поле состояния оплаты. */
    @Column(name = "is_paid")
    private boolean isPaid;

    /** Поле сумма счета в рублях. */
    @DecimalMin(value = "0.0")
    @Column(name = "bill_amount")
    private BigDecimal billAmount;

    /** Поле время и дата создания счета. */
    @Column(name = "bill_created_at")
    private LocalDateTime billCreatedAt;

    /** Поле время и дата изменения счета. */
    @Column(name = "bill_updated_at")
    private LocalDateTime billUpdatedAt;

    /** Поле время и дата оплаты счета. */
    @Column(name = "bill_paid_at")
    private LocalDateTime billPaidAt;

    /** Конструктор для счета.
     * @param billAmountParam - сумма в BigDecimal
     */
    public Bill(final BigDecimal billAmountParam) {
        this.billAmount = billAmountParam;
    }

}
