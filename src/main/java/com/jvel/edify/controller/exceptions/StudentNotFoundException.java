package com.jvel.edify.controller.exceptions;

public class StudentNotFoundException extends RuntimeException {
    private String message;

    public StudentNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public StudentNotFoundException() {

    }
}
