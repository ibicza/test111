package business.hub.orderservice.entity;


import business.hub.orderservice.entity.enums.OrderStatus;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "order_data")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<OrderItem> orderItems = new HashSet<>();

    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "closed_at")
    private Date closedAt;

    @Column(name = "address_id", nullable = false)
    private Long addressId;

    /**
     * Возвращает общую стоимость заказа.
     *
     * @return общая стоимость заказа
     */
    @Transient
    public BigDecimal getTotalOrderPrice() {
        BigDecimal sum = new BigDecimal(0);
        for (OrderItem orderItem : orderItems) {
            sum = sum.add(orderItem.getProductPrice().multiply(new BigDecimal(orderItem.getProductQuantity())));
        }
        return sum;
    }

    /**
     * Возвращает количество элементов в заказе.
     *
     * @return количество элементов в заказе
     */
    @Transient
    public int getNumberOfOrderItem() {
        return orderItems.isEmpty() ? 0 : orderItems.size();
    }

    /**
     * Обновляет элементы заказа.
     *
     * @param newOrderItems новые элементы заказа
     */
    public void updateOrderItems(final Set<OrderItem> newOrderItems) {
        orderItems.clear();

        for (OrderItem orderItem : newOrderItems) {
            addOrderItem(orderItem);
        }
    }

    /**
     * Добавляет элемент заказа.
     *
     * @param newOrderItem новый элемент заказа
     */
    public void addOrderItem(final OrderItem newOrderItem) {
        orderItems.add(newOrderItem);
    }

    /**
     * Проверяет равенство заказов.
     *
     * @param o объект для сравнения
     * @return true, если заказы равны, в противном случае - false
     */
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(id, order.id)
                && Objects.equals(userId, order.userId)
                && orderStatus == order.orderStatus
                && Objects.equals(createdAt, order.createdAt);
    }

    /**
     * Возвращает хеш-код заказа.
     *
     * @return хеш-код заказа
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, orderStatus, createdAt);
    }
}
