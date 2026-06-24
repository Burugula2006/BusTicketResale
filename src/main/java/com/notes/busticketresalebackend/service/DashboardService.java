package com.notes.busticketresalebackend.service;

import com.notes.busticketresalebackend.dto.response.DashboardStatsResponse;

public interface DashboardService {

    DashboardStatsResponse getDashboardStats(
            String email
    );
}