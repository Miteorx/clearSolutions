package com.example.clearsolutions.controller;

import com.example.clearsolutions.dto.UserRequest;
import com.example.clearsolutions.model.User;
import com.example.clearsolutions.service.UserService;
import com.example.clearsolutions.service.exceptions.InvalidBetweenDate;
import com.example.clearsolutions.service.exceptions.TooYoungException;
import com.example.clearsolutions.service.exceptions.UserNotFoundException;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserRestController {

  private final UserService userService;

  public UserRestController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUser(@PathVariable @Valid int id) throws UserNotFoundException {
    return new ResponseEntity<>(userService.readUser(id), HttpStatus.OK);
  }

  @GetMapping("/getAll")
  public ResponseEntity<List<User>> getAllUsers() {
    return ResponseEntity.ok(userService.readAll());
  }

  @GetMapping("/getBetween/{from}/{to}")
  public ResponseEntity<List<User>> getUsersBetweenDates(@PathVariable @Valid String from,
      @PathVariable String to) throws InvalidBetweenDate {
    return new ResponseEntity<>(userService.readUsersBetweenDate(from, to), HttpStatus.OK);
  }

  @PostMapping("/save")
  public ResponseEntity<User> createUser(@RequestBody @Valid UserRequest userRequest)
      throws TooYoungException {
    return new ResponseEntity<>(userService.createUser(userRequest), HttpStatus.CREATED);
  }

  @PostMapping("/update/{id}")
  public ResponseEntity<Boolean> updateUser(@RequestBody @Valid UserRequest userRequest,
      @PathVariable int id) throws UserNotFoundException {
    return new ResponseEntity<>(userService.updateUser(userRequest, id), HttpStatus.OK);
  }

  @PostMapping("/delete/{id}")
  public ResponseEntity<Boolean> deleteUser(@PathVariable @Valid int id)
      throws UserNotFoundException {
    return new ResponseEntity<>(userService.deleteUser(id), HttpStatus.OK);
  }
}
