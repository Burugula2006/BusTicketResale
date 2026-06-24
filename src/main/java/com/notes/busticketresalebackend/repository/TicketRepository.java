package com.notes.busticketresalebackend.repository;

import com.notes.busticketresalebackend.entity.Ticket;
import com.notes.busticketresalebackend.entity.TicketStatus;
import com.notes.busticketresalebackend.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface TicketRepository
        extends JpaRepository<Ticket, Long> {

    List<Ticket> findByStatus(
            TicketStatus status
    );

    List<Ticket> findBySeller(
            User seller
    );

    List<Ticket>
    findByFromCityAndToCityAndTravelDate(
            String fromCity,
            String toCity,
            LocalDate travelDate
    );

    boolean
    existsByFromCityAndToCityAndTravelDateAndBusOperatorAndSeatNumber(
            String fromCity,
            String toCity,
            LocalDate travelDate,
            String busOperator,
            String seatNumber
    );

    long countBySeller(
            User seller
    );

    long countBySellerAndStatus(
            User seller,
            TicketStatus status
    );
}