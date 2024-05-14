package business.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * Класс запуска приложения.
 *
 * @author Ostrovsky
 */

@EnableEurekaClient
@SpringBootApplication
public class
AuthServiceApplication {

    /**
     * Метод запуска приложения.
     * @param args - _
     * @author Ostrovsky
     */
    public static void main(final String[] args) {
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
