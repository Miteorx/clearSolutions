package com.example.clearsolutions.advice;

import com.example.clearsolutions.service.exceptions.InvalidBetweenDate;
import com.example.clearsolutions.service.exceptions.TooYoungException;
import com.example.clearsolutions.service.exceptions.UserNotFoundException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class AppExceptionsHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public Map<String, String> handler(MethodArgumentNotValidException ex) {
    Map<String, String> errorMap = new HashMap<>();
    ex.getBindingResult().getFieldErrors().forEach(error -> {
      errorMap.put(error.getField(), error.getDefaultMessage());
    });
    return errorMap;
  }

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler({TooYoungException.class, InvalidBetweenDate.class,
      UserNotFoundException.class})
  public Map<String, String> youngException(Exception ex) {
    Map<String, String> errorMap = new HashMap<>();
    errorMap.put("Error message", ex.getMessage());
    return errorMap;
  }
}
