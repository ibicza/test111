package business.hub.orderservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Основной класс OrderServiceApplication.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OrderServiceApplication {

    /**
     * Метод для запуска OrderServiceApplication.
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

}
