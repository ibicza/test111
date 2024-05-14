package business.hub.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс DTO продуктов.
 *
 * @author Anatoly Zakharov
 */
@Getter
@Setter
public class ProductDTO {

    /**
     * Поле наименование продукта.
     */
    @NotBlank(message = "Product name should not be empty")
    @Pattern(regexp = "^[a-zA-Zа-яА-Я0-9\\s]+$", message = "Product name should contains only letters and digits")
    @Size(min = 2, max = 50, message = "Product name should be between 2 and 50 characters ")
    private String productName;

    /**
     * Поле цена продукта в рублях.
     */
    @DecimalMin(value = "0.0")
    @Digits(integer = 10, fraction = 2, message = "Number is out of the acceptable"
            + " range (<10 digits expected>.<2 digits>)")
    private double productPrice;

    /**
     * Поле описание продукта.
     */
    @Size(min = 1, max = 255, message = "Product description should be between"
            + " 1 and 255 characters ")
    private String productDescription;

    /**
     * Поле изготовитель продукта.
     */
    @Size(min = 2, max = 50, message = "Product maker should be between 2 and 50 characters ")
    private String productManufacturer;

    /**
     * Поле вес продукта в кг.
     */
    @DecimalMin(value = "0.0")
    @Digits(integer = 4, fraction = 3, message = "Number is out of the acceptable"
            + " range (<4 digits expected>.<3 digits>)")
    private double productWeight;

    /**
     * Поле объем упаковки продукта в м3.
     */
    @DecimalMin(value = "0.0")
    @Digits(integer = 2, fraction = 6, message = "Number is out of the acceptable"
            + " range (<2 digits expected>.<6 digits>)")
    private double productVolume;

}
