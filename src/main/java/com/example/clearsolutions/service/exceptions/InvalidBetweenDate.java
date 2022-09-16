package com.example.clearsolutions.service.exceptions;

public class InvalidBetweenDate extends Exception {

  public InvalidBetweenDate() {
    super("Date 'from' isn't less then 'to'");
  }
}
