package business.hub.ConfigServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.config.server.EnableConfigServer;

/**
 * @author Anatoly Zakharov
 * # Spring Cloud Config Server
 * Spring Cloud Config Server предоставляет централизованный сервер конфигурации
 * для микросервисов, основанных на Spring Cloud.
 */
@EnableConfigServer
@EnableDiscoveryClient
@SpringBootApplication
public class ConfigServerApplication {

    /**
     * Основой метод, который запускает ConfigServerApplication.
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

}
