package com.notes.busticketresalebackend.scheduler;

import com.notes.busticketresalebackend.entity.Ticket;
import com.notes.busticketresalebackend.entity.TicketStatus;
import com.notes.busticketresalebackend.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class TicketExpiryScheduler {

    private final TicketRepository ticketRepository;

    @Scheduled(fixedRate = 60000)
    public void expireTickets() {

        List<Ticket> tickets =
                ticketRepository.findByStatus(
                        TicketStatus.AVAILABLE
                );

        LocalDateTime now =
                LocalDateTime.now();

        for (Ticket ticket : tickets) {

            LocalDateTime travelDateTime =
                    LocalDateTime.of(
                            ticket.getTravelDate(),
                            ticket.getTravelTime()
                    );

            if (travelDateTime.isBefore(now)) {

                ticket.setStatus(
                        TicketStatus.EXPIRED
                );

                ticketRepository.save(ticket);

                log.info(
                        "Ticket {} expired",
                        ticket.getId()
                );
            }
        }
    }
}