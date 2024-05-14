package business.hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlockServiceApplication {
    /**
     * Запуск микросервиса.
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(BlockServiceApplication.class, args);
    }
}
