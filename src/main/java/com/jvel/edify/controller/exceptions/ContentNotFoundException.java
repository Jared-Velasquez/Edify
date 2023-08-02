package com.jvel.edify.controller.exceptions;

public class ContentNotFoundException extends RuntimeException {
    private String message;

    public ContentNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ContentNotFoundException() {

    }
}
