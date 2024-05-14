package business.hub.cartservice.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс представляет объект корзины покупателя.
 * Корзина содержит информацию о её идентификаторе и общей стоимости продуктов в ней.
 */
@Entity
@Getter
@Setter
@Table(name = "cart")
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cartId")
    private int id;

    @Column(name = "totalPrice")
    private int totalPrice;
}
