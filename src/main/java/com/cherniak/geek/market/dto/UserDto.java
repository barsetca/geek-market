package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.User;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

 private Long id;

  @NotBlank
  @Size(min = 2)
  private String username;

  @Email
  private String email;

  private boolean enabled;

  public UserDto(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.enabled= user.isEnabled();
  }
}
