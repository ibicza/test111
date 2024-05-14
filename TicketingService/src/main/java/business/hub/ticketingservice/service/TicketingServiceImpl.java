package business.hub.ticketingservice.service;

import java.util.List;

import business.hub.ticketingservice.model.Ticket;
import business.hub.ticketingservice.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Реализация интерфейса TicketingService для управления тикетами.
 */
@Service
@RequiredArgsConstructor
public class TicketingServiceImpl implements TicketingService {

    private final TicketRepository ticketRepository;

    /**
     * Создание нового тикета.
     *
     * @param ticket Тикет для создания.
     * @return Созданный тикет.
     */
    @Override
    public Ticket createTicket(final Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /**
     * Получение всех существующих тикетов.
     *
     * @return Список всех тикетов.
     */
    @Override
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }


    /**
     * Получение тикета по его идентификатору.
     *
     * @param id Идентификатор тикета.
     * @return Тикет с указанным идентификатором.
     * @throws RuntimeException если тикет не найден.
     */
    @Override
    public Ticket getTicketById(final long id) {
        return ticketRepository.findById(id).orElseThrow(() -> new RuntimeException("Ticket not found"));
    }

    /**
     * Обновление информации о тикете.
     *
     * @param ticket Обновленная информация о тикете.
     * @return Обновленный тикет.
     */
    @Override
    public Ticket updateTicket(final Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    /**
     * Удаление тикета по его идентификатору.
     *
     * @param id Идентификатор тикета для удаления.
     */
    @Override
    public void deleteTicket(final long id) {
        ticketRepository.deleteById(id);
    }
}
