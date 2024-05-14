package bussines.hub.notificationservice.service;

import bussines.hub.notificationservice.connector.BookingServiceConnector;
import bussines.hub.notificationservice.dto.ReservationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImp implements NotificationService {
    private BookingServiceConnector bookingServiceConnector;

    @Autowired
    public NotificationServiceImp(BookingServiceConnector bookingServiceConnector) {
        this.bookingServiceConnector = bookingServiceConnector;
    }

    @Override
    public List<ReservationDTO> getFlightNumberReservations(String num) {
        return bookingServiceConnector.getFlightNumberReservations(num);
    }
}
