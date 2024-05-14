package business.hub.service;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;
/**
 * Сервис для отправки электронных писем об успешном бронировании.
 */
public interface BookingSuccessEmailService {
    /**
     * Отправляет электронное письмо об успешном бронировании.
     *
     * @param to      адрес получателя
     * @param subject тема письма
     * @param model   модель данных для использования в шаблоне
     * @throws TemplateException если возникает ошибка в процессе обработки шаблона
     * @throws IOException       если возникает ошибка ввода/вывода
     */
    void sendCancellationEmail(String to, String subject, Map<String, Object> model)
            throws TemplateException, IOException;
}
