package business.hub.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс DTO для изменения счета.
 *
 * @author Anatoly Zakharov
 */

@Data
public class UpdateBillDTO {

    /** Поле сумма счета в рублях.*/
    @DecimalMin(value = "0.0")
    private BigDecimal billAmount;

    /** Поле состояния оплаты. */
    private boolean isPaid;
}
