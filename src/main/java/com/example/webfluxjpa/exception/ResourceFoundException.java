package com.example.webfluxjpa.exception;

public class ResourceFoundException extends RuntimeException {
    public ResourceFoundException() {
    }

    public ResourceFoundException(String message) {
        super(message);
    }

    public ResourceFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResourceFoundException(Throwable cause) {
        super(cause);
    }

    public ResourceFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
