package com.notes.busticketresalebackend.service.impl;

import com.notes.busticketresalebackend.dto.request.TicketCreateRequest;
import com.notes.busticketresalebackend.dto.request.TicketUpdateRequest;
import com.notes.busticketresalebackend.dto.response.ApiResponse;
import com.notes.busticketresalebackend.dto.response.TicketResponse;
import com.notes.busticketresalebackend.entity.Ticket;
import com.notes.busticketresalebackend.entity.TicketStatus;
import com.notes.busticketresalebackend.entity.User;
import com.notes.busticketresalebackend.exception.BadRequestException;
import com.notes.busticketresalebackend.exception.ResourceNotFoundException;
import com.notes.busticketresalebackend.exception.UnauthorizedException;
import com.notes.busticketresalebackend.repository.TicketRepository;
import com.notes.busticketresalebackend.repository.UserRepository;
import com.notes.busticketresalebackend.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl
        implements TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Override
    public ApiResponse createTicket(
            TicketCreateRequest request,
            String email
    ) {

        User seller =
                userRepository.findByEmail(email)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "User not found"
                                )
                        );

        boolean exists =
                ticketRepository
                        .existsByFromCityAndToCityAndTravelDateAndBusOperatorAndSeatNumber(
                                request.getFromCity(),
                                request.getToCity(),
                                request.getTravelDate(),
                                request.getBusOperator(),
                                request.getSeatNumber()
                        );

        if (exists) {
            throw new BadRequestException(
                    "Ticket already listed"
            );
        }

        Ticket ticket = Ticket.builder()
                .fromCity(request.getFromCity())
                .toCity(request.getToCity())
                .travelDate(request.getTravelDate())
                .travelTime(request.getTravelTime())
                .busOperator(request.getBusOperator())
                .busType(request.getBusType())
                .seatNumber(request.getSeatNumber())
                .originalPrice(request.getOriginalPrice())
                .resalePrice(request.getResalePrice())
                .contactNumber(request.getContactNumber())
                .description(request.getDescription())
                .seller(seller)
                .build();

        ticketRepository.save(ticket);

        return new ApiResponse(
                "Ticket listed successfully"
        );
    }

    @Override
    public List<TicketResponse> getAvailableTickets() {

        return ticketRepository
                .findByStatus(TicketStatus.AVAILABLE)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TicketResponse> searchTickets(
            String fromCity,
            String toCity,
            LocalDate travelDate
    ) {

        return ticketRepository
                .findByFromCityAndToCityAndTravelDate(
                        fromCity,
                        toCity,
                        travelDate
                )
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ApiResponse markAsSold(
            Long ticketId,
            String email
    ) {

        Ticket ticket =
                ticketRepository.findById(ticketId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Ticket not found"
                                )
                        );

        validateOwnership(ticket, email);

        ticket.setStatus(
                TicketStatus.SOLD
        );

        ticketRepository.save(ticket);

        return new ApiResponse(
                "Ticket marked as sold"
        );
    }

    @Override
    public ApiResponse deleteTicket(
            Long ticketId,
            String email
    ) {

        Ticket ticket =
                ticketRepository.findById(ticketId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Ticket not found"
                                )
                        );

        validateOwnership(ticket, email);

        ticketRepository.delete(ticket);

        return new ApiResponse(
                "Ticket deleted successfully"
        );
    }

    @Override
    public ApiResponse updateTicket(
            Long ticketId,
            TicketUpdateRequest request,
            String email
    ) {

        Ticket ticket =
                ticketRepository.findById(ticketId)
                        .orElseThrow(
                                () -> new ResourceNotFoundException(
                                        "Ticket not found"
                                )
                        );

        validateOwnership(ticket, email);

        ticket.setResalePrice(
                request.getResalePrice()
        );

        ticket.setContactNumber(
                request.getContactNumber()
        );

        ticket.setDescription(
                request.getDescription()
        );

        ticketRepository.save(ticket);

        return new ApiResponse(
                "Ticket updated successfully"
        );
    }

    private void validateOwnership(
            Ticket ticket,
            String email
    ) {

        if (!ticket.getSeller()
                .getEmail()
                .equals(email)) {

            throw new UnauthorizedException(
                    "You can only modify your own ticket"
            );
        }
    }

    private TicketResponse mapToResponse(
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