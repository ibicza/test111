package business.hub.dto;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Класс DTO для создания нового счета.
 *
 * @author Anatoly Zakharov
 */

@Data
public class NewBillDTO {

    /** Поле сумма счета в рублях.
     */
    @DecimalMin(value = "0.0")
    private BigDecimal billAmount;
}
