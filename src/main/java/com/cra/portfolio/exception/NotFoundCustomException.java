package com.cra.portfolio.exception;

public class NotFoundCustomException extends RuntimeException {
    public NotFoundCustomException(String message) {
        super(message);
    }
}
