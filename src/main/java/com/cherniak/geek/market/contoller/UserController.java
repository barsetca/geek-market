package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.UserDto;
import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.CategoryService;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.service.UserService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/users")
public class UserController {

  private final UserService service;

  @GetMapping
  public List<UserDto> getAll(){
    return  service.findAll().stream().map(UserDto::new).collect(Collectors.toList());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteById(@PathVariable Long id) {
    User user = service.getById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User with id = %d not exists", id)));
        user.setEnabled(false);
        service.save(user);
  }

  @PutMapping("/{id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void enableById(@PathVariable Long id) {
    User user = service.getById(id).orElseThrow(() -> new ResourceNotFoundException(String.format("User with id = %d not exists", id)));
    user.setEnabled(true);
    service.save(user);
  }


}
