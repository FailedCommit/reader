package com.maat.core.exceptions;

import lombok.Data;

import static org.springframework.util.StringUtils.isEmpty;

@Data
public class MBaseCheckedException extends Exception implements MaatException {
    private String errorCode;
    private String displayMessage;

    public MBaseCheckedException(String message) {
        super(message);
    }

    public MBaseCheckedException(MaatError error) {
        super(createDebugMessage(error));
    }

    @Override
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public Exception getException() {
        return this;
    }

    /**
     * This can be used to create a more meaningful message on the UI.
     */
    @Override
    public String getDisplayMessage() {
        return displayMessage;
    }

    private static String createDebugMessage(MaatError error) {
        return isEmpty(error.getDebugMessage()) ? error.getKey() : error.getDebugMessage();
    }
}
