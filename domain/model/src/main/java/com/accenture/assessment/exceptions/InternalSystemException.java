package com.accenture.assessment.exceptions;

public class InternalSystemException extends RuntimeException {
    public InternalSystemException(String message) {
        super(message);
    }

    public InternalSystemException(String message, Throwable cause) {
        super(message, cause);
    }
}
