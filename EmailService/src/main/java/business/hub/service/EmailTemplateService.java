package business.hub.service;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;
/**
 * Сервис для обработки шаблонов электронной почты.
 */
public interface EmailTemplateService {
    /**
     * Обрабатывает шаблон электронной почты.
     *
     * @param templateName имя шаблона
     * @param model        модель данных для использования в шаблоне
     * @return строка с обработанным содержимым шаблона
     * @throws IOException       если возникает ошибка ввода/вывода
     * @throws TemplateException если возникает ошибка в процессе обработки шаблона
     */
    String processTemplate(String templateName, Map<String, Object> model) throws IOException, TemplateException;
}
