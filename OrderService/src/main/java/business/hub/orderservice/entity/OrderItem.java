package business.hub.orderservice.entity;


import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "orderItem")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "product_id", nullable = false)
    private Long productId;

    @NotNull
    @Min(1)
    @Column(name = "product_quantity", nullable = false)
    private Integer productQuantity;

    @NotNull
    @Min(0)
    @Column(name = "product_price", nullable = false)
    private BigDecimal productPrice;

    /**
     * Заказ, к которому относится элемент.
     */
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

    /**
     * Проверяет равенство элементов заказа.
     *
     * @param o объект для сравнения
     * @return true, если элементы заказа равны, в противном случае - false
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        OrderItem orderItem = (OrderItem) o;
        return productQuantity == orderItem.productQuantity
                && Objects.equals(id, orderItem.id)
                && Objects.equals(productId, orderItem.productId);
    }

    /**
     * Возвращает хеш-код элемента заказа.
     *
     * @return хеш-код элемента заказа
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, productId, productQuantity);
    }


    /**
     * Возвращает строковое представление элемента заказа.
     *
     * @return строковое представление элемента заказа
     */
    @Override
    public String toString() {
        return "OrderItem{"
                + "id=" + id
                + ", productId=" + productId
                + ", productQuantity=" + productQuantity
                + ", productPrice=" + productPrice
                + '}';
    }
}
