package com.jvel.edify.controller.exceptions;

public class ModuleNotFoundException extends RuntimeException {
    private String message;

    public ModuleNotFoundException(String message) {
        super(message);
        this.message = message;
    }

    public ModuleNotFoundException() {

    }
}
