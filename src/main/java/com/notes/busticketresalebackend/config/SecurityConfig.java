package com.notes.busticketresalebackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
                // Enable CORS
                .cors(cors -> {})

                // Disable CSRF
                .csrf(csrf -> csrf.disable())

                // Stateless JWT session
                .sessionManagement(session ->
                        session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS
                        )
                )

                // Authorization Rules
                .authorizeHttpRequests(auth -> auth

                        // Public APIs
                        .requestMatchers(
                                "/api/auth/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**"
                        )
                        .permitAll()

                        // Marketplace APIs
                        // Buyer + Seller can browse/search
                        .requestMatchers(
                                HttpMethod.GET,
                                "/api/tickets",
                                "/api/tickets/search"
                        )
                        .hasAnyRole(
                                "BUYER",
                                "SELLER"
                        )

                        // Seller-only APIs
                        .requestMatchers(
                                HttpMethod.POST,
                                "/api/tickets"
                        )
                        .hasRole("SELLER")

                        .requestMatchers(
                                HttpMethod.PUT,
                                "/api/tickets/**"
                        )
                        .hasRole("SELLER")

                        .requestMatchers(
                                HttpMethod.PATCH,
                                "/api/tickets/**"
                        )
                        .hasRole("SELLER")

                        .requestMatchers(
                                HttpMethod.DELETE,
                                "/api/tickets/**"
                        )
                        .hasRole("SELLER")

                        // Shared authenticated APIs
                        .requestMatchers(
                                "/api/users/**",
                                "/api/dashboard/**"
                        )
                        .authenticated()

                        .anyRequest()
                        .authenticated()
                )

                // JWT Filter
                .addFilterBefore(
                        jwtFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration config
    ) throws Exception {

        return config.getAuthenticationManager();
    }
}