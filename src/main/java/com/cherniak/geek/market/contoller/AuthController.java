package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.config.JwtTokenUtil;
import com.cherniak.geek.market.exception.MarketError;
import com.cherniak.geek.market.jwt.JwtRequest;
import com.cherniak.geek.market.jwt.JwtResponse;
import com.cherniak.geek.market.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserService userService;

  @PostMapping("/auth")
  public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
              jwtRequest.getPassword()));
    } catch (BadCredentialsException ex) {
      return new ResponseEntity<>(
          new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"),
          HttpStatus.UNAUTHORIZED);
    }
    UserDetails userDetails = userService.loadUserByUsername(jwtRequest.getUsername());
    String token = jwtTokenUtil.generateToken(userDetails);
    return ResponseEntity.ok(new JwtResponse(token));
  }
}
