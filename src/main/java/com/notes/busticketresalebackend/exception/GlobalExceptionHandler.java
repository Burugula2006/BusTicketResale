package com.notes.busticketresalebackend.exception;

import com.notes.busticketresalebackend.dto.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
            ResourceNotFoundException.class
    )
    public ResponseEntity<ApiResponse>
    handleNotFound(
            ResourceNotFoundException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        new ApiResponse(
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(
            BadRequestException.class
    )
    public ResponseEntity<ApiResponse>
    handleBadRequest(
            BadRequestException ex
    ) {

        return ResponseEntity
                .badRequest()
                .body(
                        new ApiResponse(
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(
            UnauthorizedException.class
    )
    public ResponseEntity<ApiResponse>
    handleUnauthorized(
            UnauthorizedException ex
    ) {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ApiResponse(
                                ex.getMessage()
                        )
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse>
    handleGeneralException(
            Exception ex
    ) {

        return ResponseEntity
                .status(
                        HttpStatus.INTERNAL_SERVER_ERROR
                )
                .body(
                        new ApiResponse(
                                "Something went wrong"
                        )
                );
    }
}