package business.hub.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Конфигурационный класс для настроек приложения.
 */
@Configuration
public class AppConfig {

    /**
     * Внедряем PasswordEncoder, который у нас = BCryptPasswordEncoder.
     * @return BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
