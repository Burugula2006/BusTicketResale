package com.notes.busticketresalebackend.controller;

import com.notes.busticketresalebackend.dto.response.DashboardStatsResponse;
import com.notes.busticketresalebackend.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {

    private final DashboardService
            dashboardService;

    @GetMapping("/stats")
    public DashboardStatsResponse
    getDashboardStats(
            Authentication authentication
    ) {

        return dashboardService
                .getDashboardStats(
                        authentication.getName()
                );
    }
}