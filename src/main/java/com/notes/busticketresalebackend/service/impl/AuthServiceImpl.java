package com.notes.busticketresalebackend.service.impl;

import com.notes.busticketresalebackend.dto.request.LoginRequest;
import com.notes.busticketresalebackend.dto.request.RegisterRequest;
import com.notes.busticketresalebackend.dto.response.ApiResponse;
import com.notes.busticketresalebackend.dto.response.AuthResponse;
import com.notes.busticketresalebackend.entity.User;
import com.notes.busticketresalebackend.repository.UserRepository;
import com.notes.busticketresalebackend.security.JwtService;
import com.notes.busticketresalebackend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl
        implements AuthService {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ApiResponse register(
            RegisterRequest request
    ) {

        if (userRepository.existsByEmail(
                request.getEmail()
        )) {

            throw new RuntimeException(
                    "Email already exists"
            );
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(
                        passwordEncoder.encode(
                                request.getPassword()
                        )
                )
                .phoneNumber(
                        request.getPhoneNumber()
                )
                .role(request.getRole())
                .build();

        userRepository.save(user);

        return new ApiResponse(
                "User registered successfully"
        );
    }

    @Override
    public AuthResponse login(
            LoginRequest request
    ) {

        User user =
                userRepository.findByEmail(
                        request.getEmail()
                ).orElseThrow(
                        () -> new RuntimeException(
                                "User not found"
                        )
                );

        boolean matches =
                passwordEncoder.matches(
                        request.getPassword(),
                        user.getPassword()
                );

        if (!matches) {
            throw new RuntimeException(
                    "Invalid credentials"
            );
        }

        String token =
                jwtService.generateToken(
                        user.getEmail(),
                        user.getRole().name()
                );

        return AuthResponse.builder()
                .token(token)
                .message("Login successful")
                .email(user.getEmail())
                .role(
                        user.getRole().name()
                )
                .build();
    }
}