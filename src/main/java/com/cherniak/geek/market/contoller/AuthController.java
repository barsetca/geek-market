package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.config.JwtTokenUtil;
import com.cherniak.geek.market.exception.MarketError;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.jwt.JwtRequest;
import com.cherniak.geek.market.jwt.JwtResponse;
import com.cherniak.geek.market.model.Role;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.RoleService;
import com.cherniak.geek.market.service.UserService;
import java.util.HashSet;
import java.util.Set;
import javax.management.relation.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
  private final RoleService roleService;

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

  @PostMapping("/reg")
  public ResponseEntity<?> registration(@RequestBody User user) throws RoleNotFoundException {

    System.out.println(user);

    if (userService.existsByUsername(user.getUsername())) {
      throw new ResourceCreationException(
          "User with username " + user.getUsername() + " already exists");
    }

    if (userService.existsByEmail(user.getEmail())) {
      throw new ResourceCreationException(
          "User with the email " + user.getEmail() + " already exists");
    }

    String password = passwordEncoder.encode(user.getPassword());
    user.setPassword(password);

    Set<Role> roles = new HashSet<>();
    Role role = roleService.findByName("ROLE_USER").orElseThrow(() ->
        new RoleNotFoundException("ROLE_USER not found"));
    roles.add(role);
    user.setRoles(roles);

    User savedUser = userService.save(user);

    System.out.println(savedUser);

    UserDetails userDetails = userService.loadUserByUsername(savedUser.getUsername());
    String token = jwtTokenUtil.generateToken(userDetails);

    return ResponseEntity.ok(new JwtResponse(token));
  }
}
