package com.example.clearsolutions.service.exceptions;

public class UserNotFoundException extends Exception {

  public UserNotFoundException() {
    super("User not found with this id");
  }
}
