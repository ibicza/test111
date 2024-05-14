package business.hub.service;

import business.hub.repository.EmailRepository;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

@Service
public class BookingCancellationEmailServiceImpl implements BookingCancellationEmailService {

    private final EmailRepository emailRepository;
    private final EmailTemplateService emailTemplateService;

    /**
     * Конструктор класса BookingCancellationEmailServiceImpl.
     *
     * @param emailRepositoryParam     репозиторий электронной почты
     * @param emailTemplateServiceParam сервис шаблонов электронной почты
     */
    @Autowired
    public BookingCancellationEmailServiceImpl(final EmailRepository emailRepositoryParam,
                                               final EmailTemplateService emailTemplateServiceParam) {
        this.emailRepository = emailRepositoryParam;
        this.emailTemplateService = emailTemplateServiceParam;
    }

    /**
     * Отправляет электронное письмо об отмене бронирования.
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
        String content = emailTemplateService.processTemplate("booking_cancellation", model);
        emailRepository.sendMail(to, subject, content, model);
    }
}
