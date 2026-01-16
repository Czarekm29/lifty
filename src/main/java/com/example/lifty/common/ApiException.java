package com.example.lifty.common;

public class ApiException extends RuntimeException {
  public ApiException(String message) {
    super(message);
  }
}
