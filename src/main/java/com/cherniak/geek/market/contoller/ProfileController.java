package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.ProfileDto;
import com.cherniak.geek.market.exception.MarketError;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.Profile;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.ProfileService;
import com.cherniak.geek.market.service.UserService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

  private final ProfileService profileService;
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  @GetMapping
  public ProfileDto getProfile(Principal principal) {
    User user = userService.getByUsername(principal.getName()).orElseThrow(() ->
        new UsernameNotFoundException(
            String.format("User by username %s not exists", principal.getName())));
    return new ProfileDto(profileService.findByUserId(user.getId()));
  }

  @PutMapping(consumes = "application/json", produces = "application/json")
  public void updateProfile(@RequestBody @Validated ProfileDto profileDto) {
    System.out.println(profileDto);

    User user = getUserAfterCheck(profileDto);
    Profile profile = ProfileDto.profileFromDto(profileDto);
    profile.setUser(user);
    user.setProfile(profile);
    userService.save(user);
  }

  @PostMapping
  public ResponseEntity<?> checkPassword(@RequestBody User user) {
    User user1 = userService.getByUsername(user.getUsername()).orElseThrow(() ->
        new ResourceNotFoundException(
            String.format("User by username %s not exists", user.getUsername())));
    System.out.println(passwordEncoder.matches(user.getPassword(), user1.getPassword()));
    if (passwordEncoder.matches(user.getPassword(), user1.getPassword())) {
      return ResponseEntity.ok(user);
    }
    return new ResponseEntity<>(
        new MarketError(HttpStatus.BAD_REQUEST.value(), "Incorrect username or password"),
        HttpStatus.BAD_REQUEST);
  }

  private User getUserAfterCheck(ProfileDto profileDto) {
    User user = userService.getById(profileDto.getUserId()).orElseThrow(() ->
        new ResourceNotFoundException(
            String.format("User by id %d not exists", profileDto.getUserId())));
    String username = profileDto.getUsername();
    if (!user.getUsername().equals(username)) {
      if (userService.existsByUsername(username)) {
        throw new ResourceCreationException("User with nick name " + username + "already exists");
      }
      user.setUsername(username);
    }

    String email = profileDto.getEmail();
    if (!user.getEmail().equals(email)) {
      if (userService.existsByEmail(email)) {
        throw new ResourceCreationException("User with email " + email + "already exists");
      }
      user.setEmail(email);
    }
    return user;
  }
}
