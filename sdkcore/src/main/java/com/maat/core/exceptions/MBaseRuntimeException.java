package com.maat.core.exceptions;

import lombok.Data;

import static org.springframework.util.StringUtils.isEmpty;

@Data
public class MBaseRuntimeException extends RuntimeException implements MaatException {
    private String errorCode;
    private String displayMessage;

    public MBaseRuntimeException(String message) {
        super(message);
    }

    public MBaseRuntimeException(MaatError error) {
        super(createDebugMessage(error));
    }

    private static String createDebugMessage(MaatError error) {
        return isEmpty(error.getDebugMessage()) ? error.getKey() : error.getDebugMessage();
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
}
