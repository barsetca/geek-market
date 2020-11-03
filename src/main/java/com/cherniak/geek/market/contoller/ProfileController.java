package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.ProfileDto;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.Profile;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.ProfileService;
import com.cherniak.geek.market.service.UserService;
import java.security.Principal;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
@Slf4j
public class ProfileController {

  private final ProfileService profileService;
  private final UserService userService;

  @GetMapping
  public ProfileDto getProfile(Principal principal) {
    User user = userService.getByUsername(principal.getName()).get();
//    profileService.findByUserUsername(principal.getName());
    //profileService.findByUserId(user.getId());
    return new ProfileDto(profileService.findByUserId(user.getId()));
  }

  @PutMapping(consumes = "application/json", produces = "application/json")
  public void updateProfile(@RequestBody ProfileDto profileDto,
      @RequestParam Map<String, String> params) {
    System.out.println(params);

    User user = getUserAfterCheck(profileDto);
    Profile profile = ProfileDto.profileFromDto(profileDto);
    profile.setUser(user);
    user.setProfile(profile);
    userService.save(user);
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
