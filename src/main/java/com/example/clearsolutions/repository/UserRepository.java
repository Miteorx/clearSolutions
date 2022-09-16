package com.example.clearsolutions.repository;

import com.example.clearsolutions.model.User;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

  User findUserById(long id);

  List<User> findUsersByBirthDateBetween(LocalDate from, LocalDate to);
}
