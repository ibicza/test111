package business.hub.orderservice;

import business.hub.orderservice.entity.Order;
import business.hub.orderservice.entity.OrderItem;
import business.hub.orderservice.entity.OrderRequest;
import business.hub.orderservice.entity.enums.OrderStatus;
import business.hub.orderservice.repository.OrderItemRepository;
import business.hub.orderservice.repository.OrderRepository;
import business.hub.orderservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = OrderService.class)
class OrderServiceApplicationTests {

    @MockBean
    private OrderRepository orderRepository;

    @MockBean
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderService orderService;

    private OrderRequest orderRequest;

    private Order kekOrder;

    private OrderItem orderItem;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);

        kekOrder = Order.builder()
                .id(1L)
                .userId(1L)
                .orderStatus(OrderStatus.ORDER_OPEN)
                .orderItems(new HashSet<>())
                .createdAt(new Date())
                .addressId(1L)
                .build();

        orderItem = new OrderItem();

        orderItem.setId(1L);
        orderItem.setOrder(kekOrder);
        orderItem.setProductId(1L);
        orderItem.setProductPrice(new BigDecimal(111));
        orderItem.setProductQuantity(11);

        orderRequest = new OrderRequest();

        orderRequest.setAddressId(1L);
        orderRequest.setUserId(1L);
        orderRequest.setOrderItems(List.of(orderItem));
    }

    @Test
    void createNewOrderTest() {

        when(orderItemRepository.save(any(OrderItem.class))).thenReturn(orderItem);
        when(orderRepository.save(any(Order.class))).thenReturn(kekOrder);

        Order savedOrder = orderService.createNewOrder(orderRequest);

        verify(orderRepository, times(1)).save(any(Order.class));
        assertEquals(kekOrder, savedOrder);
    }

    @Test
    void getOrderByIdTest() {

        Mockito.when(orderRepository.findById(kekOrder.getId())).thenReturn(Optional.of(kekOrder));
        Order res = orderService.getOrderById(kekOrder.getId());

        assertEquals(kekOrder, res);

    }

    @Test
    void getAllOrdersTest() {

        List<Order> orders = new ArrayList<>();
        orders.add(kekOrder);

        when(orderRepository.findAllByUserId(kekOrder.getId())).thenReturn(List.of(kekOrder));

        List<Order> newOrders = orderService.getAllOrders(kekOrder.getId());

        assertEquals(orders, newOrders);

    }

    @Test
    void updateOrderTest() {

        when(orderRepository.findById(kekOrder.getId())).thenReturn(Optional.of(kekOrder));
        when(orderRepository.save(kekOrder)).thenReturn(kekOrder);

        Order updatedOrder = orderService.updateOrder(kekOrder);

        assertEquals(kekOrder, updatedOrder);

    }

    @Test
    void deleteOrderTest() {

        when(orderRepository.findById(kekOrder.getId())).thenReturn(Optional.of(kekOrder));
        orderService.delete(kekOrder.getId());

        verify(orderRepository, times(1)).findById(kekOrder.getId());
        verify(orderRepository, times(1)).delete(kekOrder);

    }


}
