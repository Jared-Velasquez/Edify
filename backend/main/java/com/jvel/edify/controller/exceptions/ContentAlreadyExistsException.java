package com.jvel.edify.controller.exceptions;

public class ContentAlreadyExistsException extends RuntimeException {
    private String message;

    public ContentAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public ContentAlreadyExistsException() {

    }
}
