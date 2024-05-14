package business.hub.orderservice.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Перечисление, представляющее статусы заказа.
 */
@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    ORDER_CANCELLED("Заказ отменён"),
    ORDER_OPEN("Заказ открыт"),
    ORDER_HAD_BEEN_PAID("Заказ оплачен"),
    ORDER_DELIVERY_EXPECTED("Ожидается доставка заказа"),
    ORDER_DELIVERED("Заказ доставлен"),
    ORDER_COMPLETED_SUCCESSFULLY("Заказ успешно выполнен");

    private final String status;


    /**
     * Проверяет, является ли переданный статус допустимым статусом заказа.
     *
     * @param status статус для проверки
     * @return true, если статус допустим, в противном случае - false
     */
    public static boolean isValidOrderStatus(final String status) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.name().equalsIgnoreCase(status)) {
                return true;
            }
        }
        return false;
    }
}
