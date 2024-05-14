package business.hub.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST контроллер для тестирования.
 */
@RefreshScope
@RestController
@RequestMapping("/")
public class TestController {

    /**
     * message для тестирования.
     */
    @Value("${message}")
    private String message;

    /**
     * message для тестирования.
     * @return message.
     */
    @GetMapping("/")
    public String getString() {
        return message;
    }
}
