package com.example.userrestservice.exception;

public class UnderageException extends RuntimeException{
    public UnderageException(String message) {
        super(message);
    }
}
