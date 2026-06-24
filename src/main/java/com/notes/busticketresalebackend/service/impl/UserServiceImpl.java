package com.notes.busticketresalebackend.service.impl;

import com.notes.busticketresalebackend.dto.response.TicketResponse;
import com.notes.busticketresalebackend.dto.response.UserProfileResponse;
import com.notes.busticketresalebackend.entity.Ticket;
import com.notes.busticketresalebackend.entity.User;
import com.notes.busticketresalebackend.exception.ResourceNotFoundException;
import com.notes.busticketresalebackend.repository.TicketRepository;
import com.notes.busticketresalebackend.repository.UserRepository;
import com.notes.busticketresalebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl
        implements UserService {

    private final UserRepository userRepository;
    private final TicketRepository ticketRepository;

    @Override
    public List<TicketResponse> getMyListings(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return ticketRepository
                .findBySeller(user)
                .stream()
                .map(this::mapToTicketResponse)
                .toList();
    }

    @Override
    public UserProfileResponse getProfile(
            String email
    ) {

        User user =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        return UserProfileResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .warningCount(user.getWarningCount())
                .accountStatus(user.getAccountStatus())
                .build();
    }

    private TicketResponse mapToTicketResponse(
            Ticket ticket
    ) {

        return TicketResponse.builder()
                .id(ticket.getId())
                .fromCity(ticket.getFromCity())
                .toCity(ticket.getToCity())
                .travelDate(ticket.getTravelDate())
                .travelTime(ticket.getTravelTime())
                .busOperator(ticket.getBusOperator())
                .busType(ticket.getBusType())
                .seatNumber(ticket.getSeatNumber())
                .resalePrice(ticket.getResalePrice())
                .contactNumber(ticket.getContactNumber())
                .description(ticket.getDescription())
                .status(ticket.getStatus())
                .sellerName(
                        ticket.getSeller().getName()
                )
                .build();
    }
}