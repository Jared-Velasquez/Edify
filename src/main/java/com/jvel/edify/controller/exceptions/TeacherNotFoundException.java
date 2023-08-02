package com.jvel.edify.controller.exceptions;

public class TeacherNotFoundException extends RuntimeException {
    private String message;

    public TeacherNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public TeacherNotFoundException() {

    }
}
