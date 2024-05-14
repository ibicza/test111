package business.hub.repository;

import business.hub.service.EmailTemplateService;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 * Реализация интерфейса EmailRepository для отправки сообщения по электронной почте.
 */
@Repository
public class EmailRepositoryImpl implements EmailRepository {
    @Value("${cloud.mail.host}")
    private String mailHost;

    @Value("${cloud.mail.port}")
    private String mailPort;

    @Value("${cloud.mail.username}")
    private String username;

    @Value("${cloud.mail.password}")
    private String password;

    private final EmailTemplateService emailTemplateService;

    /**
     * Конструктор класса EmailRepositoryImpl.
     *
     * @param emailTemplateServiceParam сервис шаблонов электронной почты
     */
    public EmailRepositoryImpl(final EmailTemplateService emailTemplateServiceParam) {
        this.emailTemplateService = emailTemplateServiceParam;
    }

    /**
     * Отправляет электронное письмо.
     *
     * @param to           адрес получателя
     * @param subject      тема письма
     * @param templateName имя шаблона письма
     * @param model        модель данных для использования в шаблоне
     * @throws RuntimeException если возникает ошибка во время отправки письма
     */
    @Override
    public void sendMail(final String to,
                         final String subject,
                         final String templateName,
                         final Map<String, Object> model) {

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", mailHost);
        props.put("mail.smtp.port", mailPort);

        Session session = Session.getDefaultInstance(
                props,
                new javax.mail.Authenticator() {

                    protected PasswordAuthentication getPasswordAuthentication() {

                        return new PasswordAuthentication(username, password);
                    }
                }
        );

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);

            MimeMultipart multipart = new MimeMultipart("related");

            MimeBodyPart textPart = new MimeBodyPart();
            textPart.setContent(emailTemplateService.processTemplate(templateName, model),
                    "text/html; charset=UTF-8");
            multipart.addBodyPart(textPart);

            MimeBodyPart imagePart = new MimeBodyPart();
            DataSource fds = new FileDataSource("src/main/resources/static/logotype.jpeg");
            imagePart.setDataHandler(new DataHandler(fds));
            imagePart.setHeader("Content-ID", "<logoId>");
            multipart.addBodyPart(imagePart);

            message.setContent(multipart);

            Transport.send(message);

        } catch (MessagingException exc) {

            throw new RuntimeException(exc);
        } catch (TemplateException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
