package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.config.JwtTokenUtil;
import com.cherniak.geek.market.exception.MarketError;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.jwt.JwtRequest;
import com.cherniak.geek.market.jwt.JwtResponse;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.UserService;
import java.util.NoSuchElementException;
import javax.management.relation.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final JwtTokenUtil jwtTokenUtil;
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  @PostMapping("/auth")
  public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest jwtRequest) {
    String username = jwtRequest.getUsername();

    try {
      User user = userService.getByUsername(username).get();
      if (!user.isEnabled()) {
        return new ResponseEntity<>(
            new MarketError(HttpStatus.UNAUTHORIZED.value(), "Account deleted"),
            HttpStatus.UNAUTHORIZED);
      }
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(),
              jwtRequest.getPassword()));
    } catch (BadCredentialsException | NoSuchElementException ex) {
      return new ResponseEntity<>(
          new MarketError(HttpStatus.UNAUTHORIZED.value(), "Incorrect username or password"),
          HttpStatus.UNAUTHORIZED);
    }
    UserDetails userDetails = userService.loadUserByUsername(username);
    return getResponse(userDetails);
  }

  @PostMapping("/reg")
  public ResponseEntity<?> registration(@RequestBody @Validated User user)
      throws RoleNotFoundException {

    if (userService.existsByUsername(user.getUsername())) {
      throw new ResourceCreationException(
          "User with username " + user.getUsername() + " already exists");
    }

    if (userService.existsByEmail(user.getEmail())) {
      throw new ResourceCreationException(
          "User with the email " + user.getEmail() + " already exists");
    }
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    User savedUser = userService.create(user);
    UserDetails userDetails = userService.loadUserByUsername(savedUser.getUsername());

    return getResponse(userDetails);
  }

  private ResponseEntity<?> getResponse(UserDetails userDetails) {
    String token = jwtTokenUtil.generateToken(userDetails);
    JwtResponse jwtResponse = new JwtResponse(token, userDetails.getAuthorities());
    jwtResponse.getRoles().forEach(System.out::println);
    return ResponseEntity.ok(new JwtResponse(token, userDetails.getAuthorities()));
  }
}
