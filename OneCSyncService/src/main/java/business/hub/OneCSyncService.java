package business.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Основной класс для OneCSyncService.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class OneCSyncService {
    /**
     * Основной метод для запуска Main (OneCSyncService).
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(OneCSyncService.class, args);
    }
}
