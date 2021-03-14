package com.maat.core.exceptions;

public interface MaatException {
    String getErrorCode();
    String getMessage();
    Exception getException();
    /** This can be used to create a more meaningful message on the UI. */
    String getDisplayMessage();
}
