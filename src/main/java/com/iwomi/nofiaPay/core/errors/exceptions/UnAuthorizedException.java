package com.iwomi.nofiaPay.core.errors.exceptions;

public class UnAuthorizedException extends RuntimeException {
    private static final long serialVerisionUID = 1;
    public UnAuthorizedException(String message) {
        super(message);
    }
}
