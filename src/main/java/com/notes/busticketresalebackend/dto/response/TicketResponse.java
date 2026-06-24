package com.notes.busticketresalebackend.dto.response;

import com.notes.busticketresalebackend.entity.TicketStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class TicketResponse {

    private Long id;

    private String fromCity;

    private String toCity;

    private LocalDate travelDate;

    private LocalTime travelTime;

    private String busOperator;

    private String busType;

    private String seatNumber;

    private Double resalePrice;

    private String contactNumber;

    private String description;

    private TicketStatus status;

    private String sellerName;
}