package business.hub.bookingservice.service;

import business.hub.bookingservice.dto.ReservationDTO;
import business.hub.bookingservice.model.Reservation;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAllReservation();

    Reservation getReservation(int id);

    List<ReservationDTO> getFlightNumberReservation(String num);

    Reservation createReservation(Reservation res);

}
