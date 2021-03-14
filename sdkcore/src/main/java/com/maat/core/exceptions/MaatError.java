package com.maat.core.exceptions;

public interface MaatError {
    String getErrorCode();
    String getKey();
    /** Returns an optional message. Users must not rely on this and should defensively code while using it. */
    String getDebugMessage();
}
