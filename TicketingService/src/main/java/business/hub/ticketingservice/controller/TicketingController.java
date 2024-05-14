package business.hub.ticketingservice.controller;

import business.hub.ticketingservice.model.Ticket;
import business.hub.ticketingservice.service.TicketingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для управления тикетами.
 * Реализует создание, получение, обновление и удаление тикетов.
 */
@RestController
@Tag(name = "Main methods")
@RequiredArgsConstructor
@RequestMapping("/api/tickets")
public class TicketingController {

    private final TicketingService ticketingService;

    /**
     * Создание нового тикета.
     *
     * @param ticket Тикет для создания.
     * @return Ответ с созданным тикетом и статусом HttpStatus.CREATED.
     */
    @PostMapping
    @Operation(
            summary = "Создание нового тикета",
            description = "Возвращает созданный тикет с Id"
    )
    public ResponseEntity<Ticket> createTicket(final @RequestBody Ticket ticket) {
        return new ResponseEntity<>(ticketingService.createTicket(ticket), HttpStatus.CREATED);
    }

    /**
     * Получение всех существующих тикетов.
     *
     * @return Ответ с листом тикетов и статусом HttpStatus.OK.
     */
    @GetMapping
    @Operation(
            summary = "Получение всех существующих тикетов",
            description = "Возвращает лист тикетов"
    )
    public ResponseEntity<List<Ticket>> getAllTickets() {
        return new ResponseEntity<>(ticketingService.getAllTickets(), HttpStatus.OK);
    }

    /**
     * Получение тикета по Id.
     *
     * @param id Идентификатор тикета.
     * @return Ответ с тикетом по указанному Id и статусом HttpStatus.OK.
     */
    @GetMapping("/{id}")
    @Operation(
            summary = "Получение тикета по Id",
            description = "Возвращает тикет по указанному Id"
    )
    public ResponseEntity<Ticket> getTicketById(final @PathVariable("id") long id) {
        return new ResponseEntity<>(ticketingService.getTicketById(id), HttpStatus.OK);
    }

    /**
     * Изменение тикета.
     *
     * @param ticket Тикет для обновления.
     * @return Ответ с обновленным тикетом и статусом HttpStatus.OK.
     */
    @PutMapping
    @Operation(
            summary = "Изменение тикета",
            description = "Возвращает изменённый тикет"
    )
    public ResponseEntity<Ticket> updateTicket(final @RequestBody Ticket ticket) {
        return new ResponseEntity<>(ticketingService.updateTicket(ticket), HttpStatus.OK);
    }

    /**
     * Удаление тикета по Id.
     *
     * @param id Идентификатор тикета для удаления.
     * @return Ответ с пустым телом и статусом HttpStatus.NO_CONTENT.
     */
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Удаление тикета по Id",
            description = "Ничего не возвращает"
    )
    public ResponseEntity<Void> deleteTicket(final @PathVariable("id") long id) {
        ticketingService.deleteTicket(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

