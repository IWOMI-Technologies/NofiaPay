package com.iwomi.nofiaPay.core.errors.exceptions;

public class WrongVerificationCodeException extends RuntimeException {
    private static final long serialVerisionUID = 1;
    public WrongVerificationCodeException(String message)  {
        super(message);
    }
}
