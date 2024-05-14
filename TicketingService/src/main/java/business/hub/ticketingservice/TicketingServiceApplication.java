package business.hub.ticketingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Главный класс для TicketingServiceApplication.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TicketingServiceApplication {

    /**
     * Главный метод для запуска TicketingServiceApplication.
     *
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(TicketingServiceApplication.class, args);
    }

}
