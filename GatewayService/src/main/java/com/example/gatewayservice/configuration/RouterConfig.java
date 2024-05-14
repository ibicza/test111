package com.example.gatewayservice.configuration;

import com.example.gatewayservice.Util.RealTimeServiceLocator;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

/**
 * Класс конфигурации маршрутизации запросов к сервисам на основе путей и HTTP запросов.
 */
@Configuration
@AllArgsConstructor
public class RouterConfig {

    @Autowired
    private RealTimeServiceLocator realTimeServiceLocator;

    /**
     * Метод, конфигурирующий маршруты для Spring Cloud Gateway.
     * @param builder инструмент для построения маршрутов
     * @return объект RouteLocator, содержащий сконфигурированные маршруты
     */
    @Bean
    public RouteLocator commonRouteLocator(final RouteLocatorBuilder builder) {
        return builder.routes()
                .route("CatalogServiceGet", r -> r.path("/api/products")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("catalogService")))
                .route("CatalogServiceGet", r -> r.path("/api/products/{id}")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("catalogService")))
                .route("CatalogServicePost", r -> r.path("/api/products")
                        .and().method("POST")
                        .uri(realTimeServiceLocator.getUrl("catalogService")))
                .route("CatalogServicePut", r -> r.path("/api/products/{id}")
                        .and().method("PUT")
                        .uri(realTimeServiceLocator.getUrl("catalogService")))
                .route("CatalogServiceDelete", r -> r.path("/api/products/{id}")
                        .and().method("DELETE")
                        .uri(realTimeServiceLocator.getUrl("catalogService")))
                .route("UserServiceGet", r -> r.path("/api/users/{id}")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("userService")))
                .route("UserServiceGet", r -> r.path("/api/users")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("userService")))
                .route("UserServicePost", r -> r.path("/api/users")
                        .and().method("POST")
                        .uri(realTimeServiceLocator.getUrl("userService")))
                .route("UserServicePut", r -> r.path("/api/users")
                        .and().method("PUT")
                        .uri(realTimeServiceLocator.getUrl("userService")))
                .route("UserServiceDelete", r -> r.path("/api/users/{id}")
                        .and().method("DELETE")
                        .uri(realTimeServiceLocator.getUrl("userService")))
                .route("TicketingServiceGet", r -> r.path("/api/tickets/{id}")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("ticketingService")))
                .route("TicketingServiceGet", r -> r.path("/api/tickets")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("ticketingService")))
                .route("TicketingServicePost", r -> r.path("/api/tickets")
                        .and().method("POST")
                        .uri(realTimeServiceLocator.getUrl("ticketingService")))
                .route("TicketingServicePut", r -> r.path("/api/tickets")
                        .and().method("PUT")
                        .uri(realTimeServiceLocator.getUrl("ticketingService")))
                .route("TicketingServiceDelete", r -> r.path("/api/tickets/{id}")
                        .and().method("DELETE")
                        .uri(realTimeServiceLocator.getUrl("ticketingService")))
                .route("BillingServiceGet", r -> r.path("/api/bills/{id}")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("billingService")))
                .route("BillingServicePost", r -> r.path("/api/bills")
                        .and().method("POST")
                        .uri(realTimeServiceLocator.getUrl("billingService")))
                .route("BillingServicePut", r -> r.path("/api/bills/{id}")
                        .and().method("PUT")
                        .uri(realTimeServiceLocator.getUrl("billingService")))
                .route("BillingServiceDelete", r -> r.path("/api/bills/{id}")
                        .and().method("DELETE")
                        .uri(realTimeServiceLocator.getUrl("billingService")))
                .route("DeliveryServiceGet", r -> r.path("/api/deliveries/{id}")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("deliveryService")))
                .route("DeliveryServicePost", r -> r.path("/api/deliveries")
                        .and().method("POST")
                        .uri(realTimeServiceLocator.getUrl("deliveryService")))
                .route("DeliveryServicePut", r -> r.path("/api/deliveries/{id}")
                        .and().method("PUT")
                        .uri(realTimeServiceLocator.getUrl("deliveryService")))
                .route("DeliveryServiceDelete", r -> r.path("/api/deliveries/{id}")
                        .and().method("DELETE")
                        .uri(realTimeServiceLocator.getUrl("deliveryService")))
//                .route("OneCSyncServicePost", r -> r.path("/sync")
//                        .and().method("POST")
//                        .uri(realTimeServiceLocator.getUrl("oneCSyncService")))
//                .route("OneCSyncServiceStatusServiceGet", r -> r.path("/sync/status")
//                        .and().method("GET")
//                        .uri(realTimeServiceLocator.getUrl("oneCSyncService")))
                .route("CartServiceGet", r -> r.path("/api/cart/{userId}")
                        .and().method("GET")
                        .uri(realTimeServiceLocator.getUrl("cartService")))
                .route("CartServicePost", r -> r.path("/api/cart")
                        .and().method("POST")
                        .uri(realTimeServiceLocator.getUrl("cartService")))
                .route("CartServicePut", r -> r.path("/api/cart/{userId}/item/{itemId}")
                        .and().method("PUT")
                        .uri(realTimeServiceLocator.getUrl("cartService")))
                .route("CartServiceDelete", r -> r.path("/api/cart/{userId}/item/{itemId}")
                        .and().method("DELETE")
                        .uri(realTimeServiceLocator.getUrl("cartService")))
                .route("authenticate", r -> r.path("/api/auth/authenticate")
                        .and().method(HttpMethod.POST)
                        .uri(realTimeServiceLocator.getUrl("authenticationService")))
                .route("refreshTokens", r -> r.path("/api/auth/refreshTokens")
                        .and().method(HttpMethod.POST)
                        .uri(realTimeServiceLocator.getUrl("authenticationService")))
                .build();
    }

}
