package com.iwomi.nofiaPay.core.errors.exceptions;

public class PendingValidationException extends RuntimeException {
    private static final long serialVerisionUID = 1;
    public PendingValidationException(String message) {
        super(message);
    }
}
