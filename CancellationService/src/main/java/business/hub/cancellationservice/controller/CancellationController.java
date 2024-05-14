package business.hub.cancellationservice.controller;

import business.hub.cancellationservice.service.CancellationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cancellations")
public class CancellationController {
    private CancellationService cancellationService;

    @Autowired
    public CancellationController(CancellationService cancellationService) {
        this.cancellationService = cancellationService;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> cancelReservation(@PathVariable(name = "id") int id) {
        cancellationService.cancelReservation(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}