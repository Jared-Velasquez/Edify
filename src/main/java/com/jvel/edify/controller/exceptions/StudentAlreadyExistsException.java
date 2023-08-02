package com.jvel.edify.controller.exceptions;

public class StudentAlreadyExistsException extends RuntimeException {
    private String message;

    public StudentAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public StudentAlreadyExistsException() {

    }
}
