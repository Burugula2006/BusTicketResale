package com.notes.busticketresalebackend.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DashboardStatsResponse {

    private long totalListings;

    private long availableTickets;

    private long soldTickets;
}