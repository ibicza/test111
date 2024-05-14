package business.hub.ticketingservice;

import business.hub.ticketingservice.model.Ticket;
import business.hub.ticketingservice.repository.TicketRepository;
import business.hub.ticketingservice.service.TicketingServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TicketingServiceApplicationTests {

    @Mock
    private TicketRepository ticketRepository;
    @InjectMocks
    private TicketingServiceImpl ticketingService;

    private Ticket ticket;

    @BeforeEach
    void setUp() {

        ticket = new Ticket();
        ticket.setAddress("Street kek13");
        ticket.setId(1);
        ticket.setNumber(777);
        ticket.setEventDateTime(LocalDateTime.now());
    }


    @Test
    void createTicketTest() {
        ticketingService.createTicket(ticket);
        verify(ticketRepository, times(1)).save(ticket);
    }

    @Test
    void getAllTicketsTest() {
        List<Ticket> ticketList = new ArrayList<>();
        ticketList.add(new Ticket());
        ticketList.add(new Ticket());

        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<Ticket> newTicketList = ticketingService.getAllTickets();
        assertThat(newTicketList, containsInAnyOrder(ticketList.toArray()));
    }

    @Test
    void getTicketByIdTest() {

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        Ticket newTicket = ticketingService.getTicketById(1);
        assertEquals(ticket, newTicket);
    }

    @Test
    void updateTicketTest() {

        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Ticket newTicket = ticketingService.updateTicket(ticket);

        verify(ticketRepository).save(eq(ticket));

        assertEquals(ticket, newTicket);

    }

    @Test
    void deleteTicketTest() {

        doNothing().when(ticketRepository).deleteById(1L);
        ticketingService.deleteTicket(1L);

        verify(ticketRepository, times(1)).deleteById(1L);
    }

}
