package business.hub.ticketingservice.repository;

import business.hub.ticketingservice.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Интерфейс репозитория для работы с тикетами.
 * Расширяет JpaRepository для выполнения операций CRUD над объектами типа Ticket.
 */
@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    /**
     * Поиск тикета по его номеру.
     *
     * @param id Номер тикета.
     * @return Optional, содержащий тикет с указанным номером или пустой, если тикет не найден.
     */
    Optional<Ticket> findByNumber(long id);
}
