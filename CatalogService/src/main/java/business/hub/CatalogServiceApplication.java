package business.hub;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

/**
 * Сервис управления каталогом товаров и для фильтрации и поиска.
 * @author Anatoly Zakharov
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CatalogServiceApplication {

    /**
     * Главный метод, который запускает Spring Boot приложение.
     *
     * @param args аргументы командной строки
     */
    public static void main(final String[] args) {
        SpringApplication.run(CatalogServiceApplication.class, args);
    }

    /**
     * Создает и возвращает объект ModelMapper.
     * ModelMapper предоставляет удобные методы для маппинга объектов.
     *
     * @return объект ModelMapper
     */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
