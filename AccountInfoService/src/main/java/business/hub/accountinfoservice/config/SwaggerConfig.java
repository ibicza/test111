package business.hub.accountinfoservice.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Config-class для Swagger/OpenAPI документации.
 *
 * @version 1.0
 * @since 2024-04-23
 */
@Configuration
public class SwaggerConfig {
    /**
     * Бин для конфигурации OpenAPI.
     *
     * @return Экземпляр {@link OpenAPI} с указанным заголовком.
     */
    @Bean
    public OpenAPI api() {
        return  new OpenAPI()
                .info(
                        new Info().title("Documentation")
                );
    }
}
