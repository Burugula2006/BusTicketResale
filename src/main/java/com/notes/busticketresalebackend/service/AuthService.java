package com.notes.busticketresalebackend.service;

import com.notes.busticketresalebackend.dto.request.LoginRequest;
import com.notes.busticketresalebackend.dto.request.RegisterRequest;
import com.notes.busticketresalebackend.dto.response.ApiResponse;
import com.notes.busticketresalebackend.dto.response.AuthResponse;

public interface AuthService {

    ApiResponse register(
            RegisterRequest request
    );

    AuthResponse login(
            LoginRequest request
    );
}