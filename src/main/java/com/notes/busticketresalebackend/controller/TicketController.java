package com.notes.busticketresalebackend.controller;

import com.notes.busticketresalebackend.dto.request.TicketCreateRequest;
import com.notes.busticketresalebackend.dto.request.TicketUpdateRequest;
import com.notes.busticketresalebackend.dto.response.ApiResponse;
import com.notes.busticketresalebackend.dto.response.TicketResponse;
import com.notes.busticketresalebackend.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping
    public ApiResponse createTicket(
            @Valid
            @RequestBody
            TicketCreateRequest request,
            Authentication authentication
    ) {

        return ticketService.createTicket(
                request,
                authentication.getName()
        );
    }

    @GetMapping
    public List<TicketResponse>
    getAvailableTickets() {

        return ticketService
                .getAvailableTickets();
    }

    @GetMapping("/search")
    public List<TicketResponse>
    searchTickets(
            @RequestParam String fromCity,
            @RequestParam String toCity,
            @RequestParam LocalDate travelDate
    ) {

        return ticketService.searchTickets(
                fromCity,
                toCity,
                travelDate
        );
    }

    @PatchMapping("/{ticketId}/sold")
    public ApiResponse markAsSold(
            @PathVariable Long ticketId,
            Authentication authentication
    ) {

        return ticketService.markAsSold(
                ticketId,
                authentication.getName()
        );
    }

    @PutMapping("/{ticketId}")
    public ApiResponse updateTicket(
            @PathVariable Long ticketId,
            @Valid
            @RequestBody
            TicketUpdateRequest request,
            Authentication authentication
    ) {

        return ticketService.updateTicket(
                ticketId,
                request,
                authentication.getName()
        );
    }

    @DeleteMapping("/{ticketId}")
    public ApiResponse deleteTicket(
            @PathVariable Long ticketId,
            Authentication authentication
    ) {

        return ticketService.deleteTicket(
                ticketId,
                authentication.getName()
        );
    }
}