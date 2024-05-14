package business.hub.accountinfoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * Класс для запуска AccountInfoService микросервиса.
 *
 * Аннотации:
 * <ul>
 *     <li>{@code @SpringBootApplication} - Указывает, что этот класс является
 *     приложением Spring Boot</li>
 *     <li>{@code @EnableDiscoveryClient} - Включает регистрацию сервиса
 *     в обнаружении сервисов DiscoveryClient</li>
 * </ul>
 *
 * @see SpringApplication
 *
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AccountInfoServiceApplication {

    /**
     * Главный метод для запуска приложения Account Info Service.
     *
     * @param args - аргументы.
     */
    public static void main(final String[] args) {
        SpringApplication.run(AccountInfoServiceApplication.class, args);
    }

}
