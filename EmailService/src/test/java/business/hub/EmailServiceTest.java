package business.hub;

import business.hub.repository.EmailRepository;
import business.hub.service.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmailServiceTest {
    @Mock
    private EmailRepository emailRepository;
    @InjectMocks
    private EmailServiceImpl emailService;


    @Test
    void SendMailTest() {

        String to = "to";
        String subject = "subject";
        String template = "template";

        Map<String, Object> model = new HashMap<>();
        model.put("lol", "737");
        model.put("utka", "guse");

        emailService.sendMail(to, subject, template,model);

        verify(emailRepository, times(1)).sendMail(to, subject, template, model);
    }
}
