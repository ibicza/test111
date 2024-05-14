package business.hub.service;

import business.hub.repository.EmailRepository;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Реализация сервиса для отправки электронных писем об успешном бронировании.
 */
@Service
public class BookingSuccessEmailServiceImpl implements BookingSuccessEmailService {
    private final EmailRepository emailRepository;
    private final EmailTemplateService emailTemplateService;

    /**
     * Конструктор класса BookingSuccessEmailServiceImpl.
     *
     * @param emailRepositoryParam     репозиторий электронной почты
     * @param emailTemplateServiceParam сервис шаблонов электронной почты
     */
    public BookingSuccessEmailServiceImpl(final EmailRepository emailRepositoryParam,
                                          final EmailTemplateService emailTemplateServiceParam) {
        this.emailRepository = emailRepositoryParam;
        this.emailTemplateService = emailTemplateServiceParam;
    }

    /**
     * Отправляет электронное письмо об успешном бронировании.
     *
     * @param to      адрес получателя
     * @param subject тема письма
     * @param model   модель данных для использования в шаблоне
     * @throws TemplateException если возникает ошибка в процессе обработки шаблона
     * @throws IOException       если возникает ошибка ввода/вывода
     */
    @Override
    public void sendCancellationEmail(final String to,
                                      final String subject,
                                      final Map<String, Object> model)
            throws TemplateException, IOException {
        String content = emailTemplateService.processTemplate("order_success", model);
        emailRepository.sendMail(to, subject, content, model);
    }
}
