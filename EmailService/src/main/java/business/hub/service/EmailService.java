package business.hub.service;

import java.util.Map;
/**
 * Сервис для отправки электронной почты.
 */
public interface EmailService {

    /**
     * Отправляет электронное письмо.
     *
     * @param to           адрес получателя
     * @param subject      тема письма
     * @param templateName имя шаблона письма
     * @param model        модель данных для использования в шаблоне
     */
    void sendMail(String to, String subject, String templateName, Map<String, Object> model);
}
