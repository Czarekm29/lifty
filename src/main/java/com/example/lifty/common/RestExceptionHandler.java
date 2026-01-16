package com.example.lifty.common;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class RestExceptionHandler {

  @ExceptionHandler(ApiException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleApi(ApiException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Bad Request");
    pd.setDetail(ex.getMessage());
    return pd;
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleValidation(MethodArgumentNotValidException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Validation failed");

    Map<String, String> errors = new HashMap<>();
    for (FieldError fe : ex.getBindingResult().getFieldErrors()) {
      errors.put(fe.getField(), fe.getDefaultMessage());
    }
    pd.setProperty("errors", errors);
    return pd;
  }

  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleConstraint(ConstraintViolationException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Validation failed");
    pd.setDetail(ex.getMessage());
    return pd;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ProblemDetail handleIllegalArg(IllegalArgumentException ex) {
    ProblemDetail pd = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
    pd.setTitle("Bad Request");
    pd.setDetail(ex.getMessage());
    return pd;
  }
}
