package com.example.userrestservice.controller;


import jakarta.validation.constraints.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppControllerAdvice{
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleExceptions(@NotNull Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
