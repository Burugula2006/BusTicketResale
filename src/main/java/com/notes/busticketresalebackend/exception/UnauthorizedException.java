package com.notes.busticketresalebackend.exception;

public class UnauthorizedException
        extends RuntimeException {

    public UnauthorizedException(
            String message
    ) {
        super(message);
    }
}