package com.cherniak.geek.market.jwt;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data

public class JwtResponse {

  private String token;
  private List<String> roles;

  public JwtResponse(String token, Collection<? extends GrantedAuthority> roles) {
    this.token = token;

    this.roles = roles.stream().map(role -> role.getAuthority()).collect(Collectors.toList());
  }
}
