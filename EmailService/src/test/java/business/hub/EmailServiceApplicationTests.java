//package business.hub;
//
//import business.hub.service.EmailService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@SpringBootTest
//class EmailServiceApplicationTests {
//
//    @Autowired
//    private EmailService emailService;
//
//    @Test
//    void contextLoads() {
//
//
//    }
//
//    @Test
//    void sendMail() {
//        Map<String, Object> model = new HashMap<>();
//        model.put("firstName", "Евгений");
//        model.put("lastName", "Пригожин");
//        model.put("cost", "2402");
//        emailService.sendMail("ahmed_b13@mail.ru", "Регистрация завершена", "registration_success", model);
//        emailService.sendMail("ahmed_b13@mail.ru", "Заказ получен", "order_success", model);
//        emailService.sendMail("ahmed_b13@mail.ru", "С днём рождения!", "birthday_congratulations", model);
//    }
//}
