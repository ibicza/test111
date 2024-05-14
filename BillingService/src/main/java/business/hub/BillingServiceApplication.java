package business.hub;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * Главный класс приложения службы выставления счетов.
 */
@SpringBootApplication
@EnableDiscoveryClient
public class BillingServiceApplication {
    /**
     * Метод запуска приложения.
     *
     * @param args Аргументы командной строки
     */
    public static void main(final String[] args) {
        SpringApplication.run(BillingServiceApplication.class, args);
    }
    /**
     * Метод конфигурации для создания экземпляра ModelMapper.
     *
     * @return Экземпляр ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
