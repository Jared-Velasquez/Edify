package com.jvel.edify.controller.exceptions;

public class PositionNotFoundException extends RuntimeException {
    private String message;

    public PositionNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public PositionNotFoundException() {

    }
}
