package com.jvel.edify.controller.exceptions;

public class GenderNotFoundException extends RuntimeException {
    private String message;

    public GenderNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public GenderNotFoundException() {

    }
}
