package business.hub.cartservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс представляет объект продукта.
 * Каждый продукт имеет идентификатор, название, описание, цену, производителя и вес.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {

    private static final int MAX_PRODUCT_ID_LENGTH = 255;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_product", length = MAX_PRODUCT_ID_LENGTH, nullable = false)
    private int id;

    @Column(name = "name_product")
    @NotNull
    private String name;

    @Column(name = "description_product")
    private String description;

    @Column(name = "price_product")
    private int price;

    @Column(name = "manufacturer")
    private String manufacturer;

    @Column(name = "weight")
    private float weight;
}
