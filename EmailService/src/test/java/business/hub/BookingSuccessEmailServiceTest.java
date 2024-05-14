package business.hub;

import business.hub.repository.EmailRepository;
import business.hub.service.BookingSuccessEmailServiceImpl;
import business.hub.service.EmailTemplateService;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingSuccessEmailServiceTest {


    @Mock
    private EmailRepository emailRepository;

    @Mock
    private EmailTemplateService emailTemplateService;

    @InjectMocks
    private BookingSuccessEmailServiceImpl bookingCancellationEmailService;


    @Test
    void sendCancellationEmailTest() throws TemplateException, IOException {
        String to = "to";
        String subject = "subject";
        String template = "template";

        Map<String, Object> model = new HashMap<>();
        model.put("lol", "737");
        model.put("utka", "guse");

        when(emailTemplateService.processTemplate("order_success", model)).thenReturn("template");

        bookingCancellationEmailService.sendCancellationEmail(to, subject, model);

        verify(emailRepository, times(1)).sendMail(to, subject, template, model);
    }
}
