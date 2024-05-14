package business.hub.orderservice.exception;

import lombok.Getter;

/**
 * Исключение, выбрасываемое при отсутствии заказов для указанного пользователя.
 */
@Getter
public class OrdersNotFoundException extends RuntimeException {

    /**
     * Идентификатор пользователя, для которого не найдены заказы.
     */
    private final Long userId;

    /**
     * Конструктор исключения с указанием идентификатора пользователя.
     *
     * @param userIdParam идентификатор пользователя
     */
    public OrdersNotFoundException(final Long userIdParam) {
        super(String.format("No orders found for UserId = %d", userIdParam));
        this.userId = userIdParam;
    }
}
