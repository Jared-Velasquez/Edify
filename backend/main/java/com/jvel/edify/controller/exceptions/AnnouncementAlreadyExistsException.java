package com.jvel.edify.controller.exceptions;

public class AnnouncementAlreadyExistsException extends RuntimeException {
    private String message;

    public AnnouncementAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public AnnouncementAlreadyExistsException() {

    }
}