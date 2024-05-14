package business.hub.orderservice.service;

import business.hub.orderservice.entity.Order;
import business.hub.orderservice.entity.OrderItem;
import business.hub.orderservice.entity.OrderRequest;
import business.hub.orderservice.entity.enums.OrderStatus;
import business.hub.orderservice.exception.OrderNotFoundException;
import business.hub.orderservice.exception.OrdersNotFoundException;
import business.hub.orderservice.repository.OrderItemRepository;
import business.hub.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Сервис для работы с заказами.
 */
@Service
@Slf4j
@Transactional
@AllArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    /**
     * Создает новый заказ на основе запроса.
     *
     * @param orderRequest запрос на создание заказа
     * @return созданный заказ
     */
    public Order createNewOrder(final OrderRequest orderRequest) {

        Order newOrder = Order.builder()
                .userId(orderRequest.getUserId())
                .orderStatus(OrderStatus.ORDER_OPEN)
                .orderItems(new HashSet<>())
                .createdAt(new Date())
                .addressId(orderRequest.getAddressId())
                .build();

        newOrder = orderRepository.save(newOrder);

        for (OrderItem orderItem : orderRequest.getOrderItems()) {
            orderItem.setOrder(newOrder);
            newOrder.addOrderItem(orderItemRepository.save(orderItem));
        }
        log.info("New order was created and saved to database.");
        return newOrder;
    }

    /**
     * Получает заказ по его идентификатору.
     *
     * @param orderId идентификатор заказа
     * @return заказ с указанным идентификатором
     * @throws OrderNotFoundException если заказ не найден
     */
    public Order getOrderById(final Long orderId) {
        return orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    /**
     * Получает список всех заказов для указанного пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список заказов пользователя
     * @throws OrdersNotFoundException если для пользователя не найдены заказы
     */
    public List<Order> getAllOrders(final Long userId) {
        List<Order> orders = orderRepository.findAllByUserId(userId);
        if (orders.isEmpty()) {
            throw new OrdersNotFoundException(userId);
        }
        return orders;
    }

    /**
     * Обновляет информацию о заказе.
     *
     * @param order заказ для обновления
     * @return обновленный заказ
     */
    public Order updateOrder(final Order order) {
        Order updateOrder = getOrderById(order.getId());
        if (order.getUserId() != null && order.getUserId() != 0) {
            updateOrder.setUserId(order.getUserId());
        }
        if (order.getOrderStatus() != null) {
            updateOrder.setOrderStatus(order.getOrderStatus());
            if (order.getOrderStatus() == OrderStatus.ORDER_COMPLETED_SUCCESSFULLY
                    || order.getOrderStatus() == OrderStatus.ORDER_CANCELLED) {
                updateOrder.setClosedAt(new Date());
            }
        }
        for (OrderItem orderItem : order.getOrderItems()) {
            orderItem.setOrder(order);
            if (orderItem.getId() == null) {
                order.addOrderItem(orderItemRepository.save(orderItem));
            }
        }
        if (order.getAddressId() != null && order.getAddressId() != 0) {
            updateOrder.setAddressId(order.getAddressId());
        }

        return orderRepository.save(order);
    }

    /**
     * Удаляет заказ по его идентификатору.
     *
     * @param orderId идентификатор заказа
     */
    public void delete(final Long orderId) {
        orderRepository.delete(getOrderById(orderId));
        log.info("The order has been deleted.");
    }

    /**
     * Выполняет поиск заказов по заданным параметрам.
     *
     * @param userId      идентификатор пользователя
     * @param orderStatus статус заказа
     * @param addressId   идентификатор адреса доставки
     * @param dateFrom    дата начала периода
     * @param dateTo      дата окончания периода
     * @param pageRequest объект постраничности и сортировки
     * @return страница заказов, удовлетворяющих переданным параметрам
     */
    public Page<Order> findByParams(final Long userId,
                                    final OrderStatus orderStatus,
                                    final Long addressId,
                                    final Date dateFrom,
                                    final Date dateTo,
                                    final PageRequest pageRequest) {
        return orderRepository.findByParams(
                userId, orderStatus,
                addressId, dateFrom,
                dateTo, pageRequest);
    }

}
