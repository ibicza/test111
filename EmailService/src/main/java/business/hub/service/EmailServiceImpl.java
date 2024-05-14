package business.hub.service;

import business.hub.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
/**
 * Реализация сервиса для отправки электронной почты.
 */
@Service
public class EmailServiceImpl implements EmailService {

    private final EmailRepository emailRepository;


    /**
     * Конструктор класса EmailServiceImpl.
     *
     * @param emailRepositoryParam репозиторий электронной почты
     */
    @Autowired
    public EmailServiceImpl(final EmailRepository emailRepositoryParam) {
        this.emailRepository = emailRepositoryParam;
    }


    /**
     * Отправляет электронное письмо.
     *
     * @param to           адрес получателя
     * @param subject      тема письма
     * @param templateName имя шаблона письма
     * @param model        модель данных для использования в шаблоне
     */
    @Override
    public void sendMail(final String to,
                         final String subject,
                         final String templateName,
                         final Map<String, Object> model) {
        emailRepository.sendMail(to, subject, templateName, model);
    }

}
