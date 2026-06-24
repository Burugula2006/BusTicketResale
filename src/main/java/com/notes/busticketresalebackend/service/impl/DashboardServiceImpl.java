package com.notes.busticketresalebackend.service.impl;

import com.notes.busticketresalebackend.dto.response.DashboardStatsResponse;
import com.notes.busticketresalebackend.entity.TicketStatus;
import com.notes.busticketresalebackend.entity.User;
import com.notes.busticketresalebackend.exception.ResourceNotFoundException;
import com.notes.busticketresalebackend.repository.TicketRepository;
import com.notes.busticketresalebackend.repository.UserRepository;
import com.notes.busticketresalebackend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardServiceImpl
        implements DashboardService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Override
    public DashboardStatsResponse
    getDashboardStats(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        long totalListings =
                ticketRepository.countBySeller(
                        user
                );

        long availableTickets =
                ticketRepository
                        .countBySellerAndStatus(
                                user,
                                TicketStatus.AVAILABLE
                        );

        long soldTickets =
                ticketRepository
                        .countBySellerAndStatus(
                                user,
                                TicketStatus.SOLD
                        );

        return DashboardStatsResponse
                .builder()
                .totalListings(totalListings)
                .availableTickets(
                        availableTickets
                )
                .soldTickets(
                        soldTickets
                )
                .build();
    }
}