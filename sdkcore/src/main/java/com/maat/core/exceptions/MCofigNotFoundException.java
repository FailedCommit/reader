package com.maat.core.exceptions;

public class MCofigNotFoundException extends MBaseRuntimeException{
    private final String message;

    public MCofigNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
