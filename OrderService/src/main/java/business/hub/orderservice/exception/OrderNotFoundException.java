package business.hub.orderservice.exception;

import lombok.Getter;

/**
 * Исключение, выбрасываемое при отсутствии заказа.
 */
@Getter
public class OrderNotFoundException extends RuntimeException {

    /**
     * Идентификатор не найденного заказа.
     */
    private final Long orderId;

    /**
     * Конструктор исключения с указанием идентификатора заказа.
     *
     * @param orderIdParam идентификатор заказа
     */
    public OrderNotFoundException(final Long orderIdParam) {
        super(String.format("The order with orderId = %d is not found", orderIdParam));
        this.orderId = orderIdParam;
    }
}
