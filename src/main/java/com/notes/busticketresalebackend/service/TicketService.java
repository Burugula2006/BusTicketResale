package com.notes.busticketresalebackend.service;

import com.notes.busticketresalebackend.dto.request.TicketCreateRequest;
import com.notes.busticketresalebackend.dto.request.TicketUpdateRequest;
import com.notes.busticketresalebackend.dto.response.ApiResponse;
import com.notes.busticketresalebackend.dto.response.TicketResponse;

import java.time.LocalDate;
import java.util.List;

public interface TicketService {

    ApiResponse createTicket(
            TicketCreateRequest request,
            String email
    );

    List<TicketResponse> getAvailableTickets();

    List<TicketResponse> searchTickets(
            String fromCity,
            String toCity,
            LocalDate travelDate
    );

    ApiResponse markAsSold(
            Long ticketId,
            String email
    );

    ApiResponse deleteTicket(
            Long ticketId,
            String email
    );

    ApiResponse updateTicket(
            Long ticketId,
            TicketUpdateRequest request,
            String email
    );
}