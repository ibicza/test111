package business.hub.clientprofileservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Главный класс микросервиса ClientProfileServiceApplication.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ClientProfileServiceApplication {

    /**
     * Главный метод, который запускает микросервис.
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(ClientProfileServiceApplication.class, args);
    }

}
