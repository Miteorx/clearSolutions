package com.example.clearsolutions.service.exceptions;

public class TooYoungException extends Exception {

  public TooYoungException() {
    super("User is too young");
  }
}
