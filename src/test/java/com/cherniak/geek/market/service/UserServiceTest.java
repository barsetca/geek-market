package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.Role;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.repository.UserRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import javax.management.relation.RoleNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class UserServiceTest {

  @Autowired
  private UserService service;

  @MockBean
  private UserRepository repository;

  private User user;

  private static final String USER_NAME = "username";
  private static final String PASSWORD = "password";
  private static final String EMAIL = "email@email.com";

  @BeforeEach
  void setUp() {
    user = new User();
    Set<Role> roles = new HashSet<>();
    Role role = new Role();
    role.setId(1L);
    role.setName("USER_ROLE");
    roles.add(role);
    user.setRoles(roles);
    user.setUsername(USER_NAME);
    user.setPassword(PASSWORD);
    user.setEmail(EMAIL);
    Mockito.doReturn(user)
        .when(repository).save(user);
    Mockito.doReturn(Optional.of(user))
        .when(repository).findByUsername("username");
  }

  @Test
  void getByUsername() {
    Mockito.doReturn(Optional.of(user))
        .when(repository).findByUsername("username");
    User userActual = service.getByUsername(USER_NAME).get();
    checkUser(userActual);
    verifyMockitoUserName(1);
  }

  @Test
  void getById() {
    Mockito.doReturn(Optional.of(user))
        .when(repository).findById(1L);
    User userActual = service.getById(1L).get();
    checkUser(userActual);
    Mockito.verify(repository, Mockito.times(1))
        .findById(ArgumentMatchers.anyLong());
    Mockito.verify(repository, Mockito.times(1))
        .findById(ArgumentMatchers.eq(1L));
  }

  @Test
  void existsByUsername() {
  }

  @Test
  void existsByEmail() {
    Mockito.doReturn(Boolean.valueOf(true))
        .when(repository).existsByEmail(EMAIL);
    Assertions.assertTrue(service.existsByEmail(EMAIL));

  }

  @Test
  void save() {
    User userActual = service.save(user);
    checkUser(userActual);
    verifyMockitoUser();
  }


  @Test
  void findAll() {
    List<User> userList = new ArrayList<>(Arrays.asList(user));
    Mockito.doReturn(userList)
        .when(repository).findAll();
    Assertions.assertEquals(1, service.findAll().size());
  }

  @Test
  void create() throws RoleNotFoundException {
    User userActual = service.create(user);
    checkUser(userActual);
    Assertions.assertEquals("ROLE_USER",
        userActual.getRoles().stream().findFirst().get().getName());
    verifyMockitoUser();
  }

  @Test
  void loadUserByUsername() {
    Assertions.assertAll(() ->
        {
          Assertions
              .assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("user"));
        },
        () -> {
          Assertions.assertEquals(USER_NAME, service.loadUserByUsername(USER_NAME).getUsername());
        });

    verifyMockitoUserName(2);
    Mockito.verify(repository, Mockito.times(1))
        .findByUsername(ArgumentMatchers.eq("user"));


  }

  private void verifyMockitoUser() {
    Mockito.verify(repository, Mockito.times(1))
        .save(ArgumentMatchers.any(User.class));
    Mockito.verify(repository, Mockito.times(1))
        .save(ArgumentMatchers.eq(user));
  }

  private void verifyMockitoUserName(int countString) {
    Mockito.verify(repository, Mockito.times(countString))
        .findByUsername(ArgumentMatchers.anyString());
    Mockito.verify(repository, Mockito.times(1))
        .findByUsername(ArgumentMatchers.eq(USER_NAME));
  }

  private void checkUser(User userActual) {
    Assertions.assertAll(() -> {
          Assertions.assertEquals(USER_NAME, userActual.getUsername());
        },
        () -> {
          Assertions.assertEquals(PASSWORD, userActual.getPassword());
        },
        () -> {
          Assertions.assertEquals(EMAIL, userActual.getEmail());
        },
        () -> {
          Assertions.assertEquals(user, userActual);
        });
  }
}