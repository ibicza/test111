package business.hub.userinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;


/**
 * Главный класс для UserInfoServiceApplication.
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class UserInfoServiceApplication {
    /**
     * Главный метод для запуска UserInfoServiceApplication.
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(UserInfoServiceApplication.class, args);
    }
}
