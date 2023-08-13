package com.jvel.edify.controller.exceptions;

public class CourseNotFoundException extends RuntimeException {
    private String message;

    public CourseNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public CourseNotFoundException() {

    }
}
