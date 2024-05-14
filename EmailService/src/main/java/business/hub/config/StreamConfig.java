package business.hub.config;

import business.hub.dto.BirthdayDTO;
import business.hub.repository.EmailRepository;
import business.hub.service.EmailTemplateService;
import com.fasterxml.jackson.databind.ObjectMapper;
import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Конфигурационный класс для настройки обработчика потока.
 */
@Configuration
public class StreamConfig {

    private final EmailTemplateService emailTemplateService;
    private final EmailRepository emailRepository;
    private final ObjectMapper objectMapper;


    /**
     * Конструктор класса StreamConfig.
     *
     * @param emailTemplateServiceParam сервис шаблонов электронной почты
     * @param emailRepositoryParam      репозиторий электронной почты
     * @param objectMapperParam         объект для преобразования объектов Java в JSON и обратно
     */
    public StreamConfig(final EmailTemplateService emailTemplateServiceParam,
                        final EmailRepository emailRepositoryParam,
                        final ObjectMapper objectMapperParam) {
        this.emailTemplateService = emailTemplateServiceParam;
        this.emailRepository = emailRepositoryParam;
        this.objectMapper = objectMapperParam;
    }

    /**
     * Метод для обработки информации о дне рождения.
     *
     * @return объект Consumer, принимающий строку с информацией о дне рождения
     */
    @Bean
    public Consumer<String> processBirthdayInfo() {
        return message -> {
            System.out.println("Received birthday info: " + message);
            try {
                BirthdayDTO birthdayInfo = objectMapper.readValue(message, BirthdayDTO.class);
                String content = emailTemplateService.processTemplate("birthday_template",
                        (Map<String, Object>) birthdayInfo);
                emailRepository.sendMail(birthdayInfo.getEmail(), "Happy Birthday!", content,
                        (Map<String, Object>) birthdayInfo);
            } catch (TemplateException | IOException e) {
                throw new RuntimeException("Error processing birthday info", e);
            }
        };
    }
}


