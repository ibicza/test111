package business.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EmailServiceApplication {

    /**
     * Основной метод для запуска EmailServiceApplication.
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(EmailServiceApplication.class, args);
    }
}
