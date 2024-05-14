package business.hub.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Конфигурационный класс для настройки Swagger.
 */
@Configuration
public class SwaggerConfig {
    /**
     * Бин для конфигурации OpenAPI.
     *
     * @return объект OpenAPI с информацией о документации
     */
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .servers(
                        List.of(
                                new Server().url("http://localhost:8080")
                        )
                )
                .info(
                        new Info().title("Block APi")
                );
    }
}
