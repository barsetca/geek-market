package com.cherniak.geek.market.jwt;

import lombok.Data;

@Data
public class JwtRequest {
  private String username;
  private String password;
}
