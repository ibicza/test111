package bussines.hub.notificationservice.util;

import bussines.hub.notificationservice.dto.ReservationDTO;
import bussines.hub.notificationservice.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class testNotification {

    private final NotificationService notificationService;
    private final KafkaTemplate kafkaTemplate;
    private final int max = 4;
    private final String num = String.valueOf(rnd(max));

    @Autowired
    public testNotification(NotificationService notificationService, KafkaTemplate kafkaTemplate) {
        this.notificationService = notificationService;
        this.kafkaTemplate = kafkaTemplate;
    }

    public int rnd(int max) {
        return (int) (Math.random() * ++max);
    }

    @Scheduled(cron = "* * */2 * * *")
    public void cancelledFlightTest() {
        List<ReservationDTO> reservList = notificationService.getFlightNumberReservations(num);
        for (ReservationDTO r : reservList) {
            kafkaTemplate.send("email-cancelled", r);
        }
    }

    @Scheduled(cron = "* * */5 * * *")
    public void changeFlightTest() {
        List<ReservationDTO> reservList = notificationService.getFlightNumberReservations(num);
        for (ReservationDTO r : reservList) {
            kafkaTemplate.send("email-change", r);
        }
    }
}
