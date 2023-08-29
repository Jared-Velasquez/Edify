package com.jvel.edify.controller.exceptions;

public class ModuleAlreadyExistsException extends RuntimeException {
    private String message;

    public ModuleAlreadyExistsException(String message) {
        super(message);
        this.message = message;
    }

    public ModuleAlreadyExistsException() {

    }
}
