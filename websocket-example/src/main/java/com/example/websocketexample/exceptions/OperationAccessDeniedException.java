package com.example.websocketexample.exceptions;

public class OperationAccessDeniedException extends RuntimeException {
    public OperationAccessDeniedException(String message) {
        super(message);
    }
}
