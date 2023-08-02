package com.jvel.edify.controller.exceptions;

public class UserAlreadyExistsException extends RuntimeException {
    private String message;

    public UserAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public UserAlreadyExistsException() {

    }
}
