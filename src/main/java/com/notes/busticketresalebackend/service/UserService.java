package com.notes.busticketresalebackend.service;

import com.notes.busticketresalebackend.dto.response.TicketResponse;
import com.notes.busticketresalebackend.dto.response.UserProfileResponse;

import java.util.List;

public interface UserService {

    List<TicketResponse> getMyListings(
            String email
    );

    UserProfileResponse getProfile(
            String email
    );
}