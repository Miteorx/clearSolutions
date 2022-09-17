package com.example.clearsolutions.controller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.clearsolutions.dto.UserRequest;
import com.example.clearsolutions.model.User;
import com.example.clearsolutions.service.UserService;
import com.example.clearsolutions.service.exceptions.UserNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRestController.class)
public class UserRestControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  public void getAllUsers() throws Exception {
    LocalDate localDate = LocalDate.of(2000, 12, 12);
    User user = new User(0L, "liza@gmail.com", "Liza", "Liza", localDate, null, null);
    List<User> userList = List.of(user);

    given(userService.readAll()).willReturn(userList);

    mockMvc.perform(get("/users/getAll")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].firstName", is(user.getFirstName())));
  }

  @Test
  public void getUserById() throws Exception {
    LocalDate localDateFirst = LocalDate.of(2000, 12, 12);
    LocalDate localDateSecond = LocalDate.of(2001, 12, 12);
    User user = new User(0L, "liza@gmail.com", "Liza", "Liza", localDateFirst, null, null);
    User user2 = new User(1L, "bob@gmail.com", "Bob", "Bob", localDateSecond, null, null);

    List<User> userList = Arrays.asList(user, user2);

    given(userService.readUser(0)).willReturn(userList.get(0));

    mockMvc.perform(get("/users/0")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("firstName", is(user.getFirstName())));
  }

  @Test
  public void getUserByWrongId() throws Exception {
    LocalDate localDateFirst = LocalDate.of(2000, 12, 12);
    LocalDate localDateSecond = LocalDate.of(2001, 12, 12);
    User user = new User(0L, "liza@gmail.com", "Liza", "Liza", localDateFirst, null, null);
    User user2 = new User(1L, "bob@gmail.com", "Bob", "Bob", localDateSecond, null, null);

    List<User> userList = Arrays.asList(user, user2);

    given(userService.readUser((int) (userList.get(userList.size() - 1).getId() + 1))).willThrow(
        UserNotFoundException.class);

    mockMvc.perform(get("/users/2")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isInternalServerError());
  }

  @Test
  public void createUser() throws Exception {
    LocalDate localDateFirst = LocalDate.of(2000, 12, 12);

    UserRequest userRequest = new UserRequest("liza@gmail.com", "Liza", "Liza", localDateFirst,
        null, null);
    User user = new User(0L, userRequest.getEmail(), userRequest.getFirstName(),
        userRequest.getLastName(), userRequest.getBirthDate(), userRequest.getAddress(),
        userRequest.getPhoneNumber());

    when(userService.createUser(userRequest)).thenReturn(user);

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users/save")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(
            "{\"id\":0,\"email\":\"liza@gmail.com\",\"firstName\":\"Liza\",\"lastName\":\"Liza\","
                + "\"birthDate\":\"2000-12-12\",\"address\":null,\"phoneNumber\":null}");

    // I know that I should use some parser from String to JSON, but I don't know which parser I can use :(

    mockMvc.perform(mockRequest)
        .andExpect(status().isCreated());
  }


  @Test
  public void updateUser() throws Exception {
    LocalDate localDateFirst = LocalDate.of(2000, 12, 12);
    UserRequest userRequest = new UserRequest("liza@gmail.com", "Liza", "Liza", localDateFirst,
        null, null);
    User user = new User(0L, userRequest.getEmail(), userRequest.getFirstName(),
        userRequest.getLastName(), userRequest.getBirthDate(), userRequest.getAddress(),
        userRequest.getPhoneNumber());

    when(userService.updateUser(userRequest, 1)).thenReturn(true);

    MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/users/update/1")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(
            "{\"id\":1,\"email\":\"liza@gmail.com\",\"firstName\":\"Liza\",\"lastName\":\"Liza\","
                + "\"birthDate\":\"2000-12-12\",\"address\":null,\"phoneNumber\":null}");

    // I know that I should use some parser from String to JSON, but I don't know which parser I can use :(

    mockMvc.perform(mockRequest)
        .andExpect(status().isOk());
  }
}
