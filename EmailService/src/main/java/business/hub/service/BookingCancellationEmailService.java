package business.hub.service;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

public interface BookingCancellationEmailService {
    /**
     * Отправляет электронное письмо об отмене бронирования.
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
