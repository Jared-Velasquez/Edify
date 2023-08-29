package com.jvel.edify.controller.exceptions;

public class AssignmentAlreadyExistsException extends RuntimeException {
    private String message;

    public AssignmentAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public AssignmentAlreadyExistsException() {

    }
}
