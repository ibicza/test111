package business.hub.orderservice.controller;

import business.hub.orderservice.entity.Order;
import business.hub.orderservice.entity.OrderRequest;
import business.hub.orderservice.entity.enums.OrderStatus;
import business.hub.orderservice.entity.search.OrderSearchValues;
import business.hub.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Date;
import java.util.List;
/**
 * Контроллер для обработки операций с заказами.
 */
@RestController
@Slf4j
@RequestMapping("/order")
@AllArgsConstructor
@Validated
@Tag(name = "Main methods")
public class OrderController {
    /**
     * Сервис для выполнения операций с заказами.
     */
    private final OrderService orderService;

    /**
     * Создание нового заказа.
     *
     * @param orderRequest объект запроса на создание заказа
     * @return ответ с созданным заказом и его Id
     */
    @PostMapping
    @Operation(
            summary = "Создание нового заказа",
            description = "Возвращает созданный заказ с Id"
    )
    public ResponseEntity<Order> createNewOrder(
            final @Valid @RequestBody OrderRequest orderRequest) {
        log.info("Received order to add order.");
        return ResponseEntity.ok(orderService.createNewOrder(orderRequest));
    }

    /**
     * Получение заказа по его Id.
     *
     * @param orderId Id заказа
     * @return ответ с заказом
     */
    @PostMapping("/id")
    @Operation(
            summary = "Получение заказа по его Id",
            description = "Возвращает заказ"
    )
    public ResponseEntity<Order> getOrderById(final @RequestBody Long orderId) {
        log.info("Received request to get order");
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    /**
     * Получение списка заказов определённого пользователя.
     *
     * @param userId Id пользователя
     * @return ответ со списком заказов
     */
    @PostMapping("/user")
    @Operation(
            summary = "Получение списка заказов определённого пользователя",
            description = "Возвращает список заказов"
    )
    public ResponseEntity<List<Order>> getAllOrders(final @RequestBody Long userId) {
        log.info("Received request to get all orders");
        return ResponseEntity.ok(orderService.getAllOrders(userId));
    }

    /**
     * Обновление существующего заказа.
     *
     * @param order заказ для обновления
     * @return ответ с обновлённым заказом
     */
    @PostMapping("/update")
    @Operation(
            summary = "Обновление существующего заказа",
            description = "Возвращает обновлённый заказ"
    )
    public ResponseEntity<Order> updateOrder(final @RequestBody Order order) {
        log.info("Received request to update order");

        if (order.getId() == null || order.getId() == 0) {
            return new ResponseEntity("missing id parameter",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (order.getUserId() == null || order.getUserId() == 0) {
            return new ResponseEntity("missing user_id parameter",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        if (order.getAddressId() == null || order.getAddressId() == 0) {
            return new ResponseEntity("missing address_id parameter",
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }

        return ResponseEntity.ok(orderService.updateOrder(order));
    }
    /**
     * Удаление заказа по его Id.
     *
     * @param orderId Id заказа для удаления
     * @return ответ со статусом операции
     */
    @PostMapping("/delete")
    @Operation(
            summary = "Удаление заказа по его Id",
            description = "Возвращает статус операции"
    )
    public ResponseEntity deleteOrder(final @RequestBody Long orderId) {
        log.info("Received request to delete order");
        orderService.delete(orderId);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * Поиск объекта по любым параметрам.
     *
     * @param orderSearchValues объект с параметрами поиска заказов
     * @return ответ с объектом Page, содержащим список заказов
     */
    @PostMapping("/search")
    @Operation(
            summary = "Получение списка заказов по параметрам",
            description = "Возвращает список заказов в объекте Page"
    )
    public ResponseEntity<Page<Order>> searchOrdersByParams(
            final @RequestBody OrderSearchValues orderSearchValues) {
        log.info("Received request to get order by params");

        Long userId = orderSearchValues.getUserId();
        OrderStatus status = orderSearchValues.getOrderStatus();
        Long addressId = orderSearchValues.getAddressId();

        Date dateFrom = orderSearchValues.getDateFrom();
        Date dateTo = orderSearchValues.getDateTo();

        Integer pageNumber = orderSearchValues.getPageNumber();
        Integer pageSize = orderSearchValues.getPageSize();

        String sortColumn = orderSearchValues.getSortColumn();
        String sortDirection = orderSearchValues.getSortDirection();

        Sort.Direction direction = sortDirection == null
                || sortDirection.trim().length() == 0
                || sortDirection.trim().equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        // объект сортировки, который содержит столбец и направление
        Sort sort = Sort.by(direction, sortColumn, "id");

        // объект постраничности
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        Page<Order> result = orderService.findByParams(
                userId, status, addressId, dateFrom, dateTo, pageRequest);

        return ResponseEntity.ok(result);
    }
}
