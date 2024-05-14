package business.hub.ticketingservice.service;
import business.hub.ticketingservice.model.Ticket;

import java.util.List;

/**
 * Интерфейс сервиса для управления тикетами.
 * Определяет методы для создания, получения, обновления и удаления тикетов.
 */
public interface TicketingService {

    /**
     * Создание нового тикета.
     *
     * @param ticket Тикет для создания.
     * @return Созданный тикет.
     */
    Ticket createTicket(Ticket ticket);

    /**
     * Получение всех существующих тикетов.
     *
     * @return Список всех тикетов.
     */
    List<Ticket> getAllTickets();

    /**
     * Получение тикета по его идентификатору.
     *
     * @param id Идентификатор тикета.
     * @return Тикет с указанным идентификатором.
     */
    Ticket getTicketById(long id);

    /**
     * Обновление информации о тикете.
     *
     * @param ticket Обновленная информация о тикете.
     * @return Обновленный тикет.
     */
    Ticket updateTicket(Ticket ticket);

    /**
     * Удаление тикета по его идентификатору.
     *
     * @param id Идентификатор тикета для удаления.
     */
    void deleteTicket(long id);
}
