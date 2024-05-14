package business.hub.cleaningrefreshtokensservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CleaningRefreshTokensServiceApplication {
    /**
     * Класс для запуска CleaningRefreshTokensService.
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(CleaningRefreshTokensServiceApplication.class, args);
    }
}
