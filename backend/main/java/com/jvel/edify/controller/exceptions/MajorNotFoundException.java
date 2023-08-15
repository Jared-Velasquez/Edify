package com.jvel.edify.controller.exceptions;

public class MajorNotFoundException extends RuntimeException {
    private String message;

    public MajorNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public MajorNotFoundException() {

    }
}
