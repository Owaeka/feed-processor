package com.sportygroup.feedprocessor.exception;

public class UnsupportedMessageException extends RuntimeException {

    public UnsupportedMessageException(String message) {
        super(message);
    }

    public UnsupportedMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
