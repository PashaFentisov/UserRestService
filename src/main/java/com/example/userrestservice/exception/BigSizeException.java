package com.example.userrestservice.exception;

public class BigSizeException extends RuntimeException{

  public BigSizeException(String message) {
    super(message);
  }
}
