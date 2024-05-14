package bussines.hub.notificationservice.service;

import bussines.hub.notificationservice.dto.ReservationDTO;

import java.util.List;

public interface NotificationService {

    List<ReservationDTO> getFlightNumberReservations(String num);
}
