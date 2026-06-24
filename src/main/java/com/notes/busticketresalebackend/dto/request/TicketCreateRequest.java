package com.notes.busticketresalebackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class TicketCreateRequest {

    @NotBlank
    private String fromCity;

    @NotBlank
    private String toCity;

    @NotNull
    private LocalDate travelDate;

    @NotNull
    private LocalTime travelTime;

    @NotBlank
    private String busOperator;

    @NotBlank
    private String busType;

    @NotBlank
    private String seatNumber;

    @NotNull
    private Double originalPrice;

    @NotNull
    private Double resalePrice;

    @NotBlank
    private String contactNumber;

    private String description;
}