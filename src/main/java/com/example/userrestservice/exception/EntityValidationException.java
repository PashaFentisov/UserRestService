package com.example.userrestservice.exception;

public class EntityValidationException extends RuntimeException{
    public EntityValidationException(String message) {
        super(message);
    }
}
