package business.hub.cartservice;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CartServiceApplication {

    /**
     * Метод для запуска CartServiceApplication.
     * @param args - _
     */
    public static void main(final String[] args) {
        SpringApplication.run(CartServiceApplication.class, args);
    }

    @PostConstruct
    void init() {

    }
}
