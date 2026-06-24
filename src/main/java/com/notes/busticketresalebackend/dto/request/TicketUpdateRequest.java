package com.notes.busticketresalebackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TicketUpdateRequest {

    @NotNull
    private Double resalePrice;

    @NotBlank
    private String contactNumber;

    private String description;
}