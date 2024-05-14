package business.hub.bookingservice.controller;

import business.hub.bookingservice.model.Reservation;
import business.hub.bookingservice.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    private final ReservationService reservationService;

    @Autowired
    public ReservationController(ReservationService service) {
        this.reservationService = service;
    }


    @GetMapping
    public ResponseEntity<List<Reservation>> getAllReservations() {
        return new ResponseEntity<>(reservationService.getAllReservation(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservation(@PathVariable("id") int id) {
        return new ResponseEntity<>(reservationService.getReservation(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> createReservation(@RequestBody @Valid Reservation res, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(bindingResult.getAllErrors(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(reservationService.createReservation(res), HttpStatus.CREATED);
    }
}
