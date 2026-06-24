package com.notes.busticketresalebackend.controller;

import com.notes.busticketresalebackend.dto.response.TicketResponse;
import com.notes.busticketresalebackend.dto.response.UserProfileResponse;
import com.notes.busticketresalebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/my-listings")
    public List<TicketResponse>
    getMyListings(
            Authentication authentication
    ) {

        return userService.getMyListings(
                authentication.getName()
        );
    }

    @GetMapping("/profile")
    public UserProfileResponse getProfile(
            Authentication authentication
    ) {

        return userService.getProfile(
                authentication.getName()
        );
    }
}