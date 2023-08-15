package com.jvel.edify.controller.exceptions;

public class TeacherAlreadyExistsException extends RuntimeException {
    private String message;

    public TeacherAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public TeacherAlreadyExistsException() {

    }
}
