package com.example.clearsolutions.service;

import com.example.clearsolutions.dto.UserRequest;
import com.example.clearsolutions.model.User;
import com.example.clearsolutions.repository.UserRepository;
import com.example.clearsolutions.service.exceptions.InvalidBetweenDate;
import com.example.clearsolutions.service.exceptions.TooYoungException;
import com.example.clearsolutions.service.exceptions.UserNotFoundException;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User createUser(UserRequest userRequest) throws TooYoungException {
    LocalDate currentDate = LocalDate.now();
    if (Period.between(userRequest.getBirthDate(), currentDate).getYears() < 18) {
      throw new TooYoungException();
    }
    User user = new User(0L, userRequest.getEmail(), userRequest.getFirstName(),
        userRequest.getLastName(), userRequest.getBirthDate(), userRequest.getAddress(),
        userRequest.getPhoneNumber());
    return userRepository.save(user);
  }

  public List<User> readAll() {
    return (List<User>) userRepository.findAll();
  }

  public User readUser(int id) throws UserNotFoundException {
    User user = userRepository.findUserById(id);
    if (user == null) {
      throw new UserNotFoundException();
    }
    return user;
  }

  public List<User> readUsersBetweenDate(String from, String to) throws InvalidBetweenDate {
    LocalDate fromDate = LocalDate.parse(from);
    LocalDate toDate = LocalDate.parse(to);
    if (fromDate.isAfter(toDate)) {
      throw new InvalidBetweenDate();
    }
    return userRepository.findUsersByBirthDateBetween(fromDate, toDate);
  }

  public boolean updateUser(UserRequest userRequest, int id) throws UserNotFoundException {
    if (userRepository.existsById((long) id)) {
      User user = new User((long) id, userRequest.getEmail(), userRequest.getFirstName(),
          userRequest.getLastName(), userRequest.getBirthDate(), userRequest.getAddress(),
          userRequest.getPhoneNumber());
      userRepository.save(user);
      return true;
    } else {
      throw new UserNotFoundException();
    }
  }

  public boolean deleteUser(int id) throws UserNotFoundException {
    if (userRepository.existsById((long) id)) {
      userRepository.deleteById((long) id);
      return true;
    } else {
      throw new UserNotFoundException();
    }
  }
}
