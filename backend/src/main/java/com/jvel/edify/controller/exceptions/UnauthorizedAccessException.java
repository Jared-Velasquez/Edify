package com.jvel.edify.controller.exceptions;

public class UnauthorizedAccessException extends RuntimeException {
    private String message;

    public UnauthorizedAccessException(String message) {
        super(message);
        this.message = message;
    }

    public UnauthorizedAccessException() {

    }
}
