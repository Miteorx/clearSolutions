package com.example.clearsolutions.dto;

import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import org.springframework.format.annotation.DateTimeFormat;

public class UserRequest {

  @Email(message = "invalid email")
  @NotNull(message = "email should be not null")
  private String email;

  @NotNull(message = "first name should be not null")
  private String firstName;

  @NotNull(message = "last name should be not null")
  private String lastName;

  @NotNull(message = "birth data should be not null")
  @Past(message = "date of birth must be less than today")
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthDate;
  private String address;
  private String phoneNumber;

  public UserRequest(String email, String firstName, String lastName, LocalDate birthDate,
      String address, String phoneNumber) {
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.address = address;
    this.phoneNumber = phoneNumber;
  }

  public String getEmail() {
    return email;
  }


  public String getFirstName() {
    return firstName;
  }


  public String getLastName() {
    return lastName;
  }


  public LocalDate getBirthDate() {
    return birthDate;
  }


  public String getAddress() {
    return address;
  }


  public String getPhoneNumber() {
    return phoneNumber;
  }

}
