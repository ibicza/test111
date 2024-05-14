package business.hub.service;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * Реализация сервиса для обработки шаблонов электронной почты.
 */
@Service
public class EmailTemplateServiceImpl implements EmailTemplateService {

    private final Configuration freemarkerConfig;


    /**
     * Конструктор класса EmailTemplateServiceImpl.
     */
    public EmailTemplateServiceImpl() {
        freemarkerConfig = new Configuration(Configuration.VERSION_2_3_31);
        freemarkerConfig.setDefaultEncoding("UTF-8");
        freemarkerConfig.setClassForTemplateLoading(this.getClass(), "/ftl");
    }


    /**
     * Обрабатывает шаблон электронной почты.
     *
     * @param templateName имя шаблона
     * @param model        модель данных для использования в шаблоне
     * @return строка с обработанным содержимым шаблона
     * @throws IOException       если возникает ошибка ввода/вывода
     * @throws TemplateException если возникает ошибка в процессе обработки шаблона
     */
    @Override
    public String processTemplate(final String templateName,
                                  final Map<String, Object> model)
            throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName + ".ftl");
        StringWriter result = new StringWriter();
        template.process(model, result);
        return result.toString();
    }

}
