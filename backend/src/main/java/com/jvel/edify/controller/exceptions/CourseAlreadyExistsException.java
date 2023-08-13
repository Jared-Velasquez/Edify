package com.jvel.edify.controller.exceptions;

public class CourseAlreadyExistsException extends RuntimeException {
    private String message;

    public CourseAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public CourseAlreadyExistsException() {

    }
}
