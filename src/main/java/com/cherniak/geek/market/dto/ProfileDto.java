package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Profile;
import java.time.LocalDate;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProfileDto {

  private Long id;

  private String firstname;
  private String surname;
  private String phone;
  private LocalDate birthday;
  private String sex;
  private String city;
  private String email;
  private String username;
  private Long userId;

  public ProfileDto(Profile profile) {
    this.id = profile.getId();
    this.firstname = profile.getFirstname();
    this.surname = profile.getSurname();
    this.phone = profile.getPhone();
    this.birthday = profile.getBirthday();
    this.sex = profile.getSex();
    this.city = profile.getCity();
    this.email = profile.getUser().getEmail();
    this.username = profile.getUser().getUsername();
    this.userId = profile.getUser().getId();
  }

  public static Profile profileFromDto(ProfileDto profileDto) {
    Profile profile = new Profile();
    profile.setId(profileDto.getId());
    profile.setBirthday(profileDto.getBirthday());
    profile.setCity(profileDto.getCity());
    profile.setFirstname(profileDto.getFirstname());
    profile.setSurname(profileDto.getSurname());
    profile.setPhone(profileDto.getPhone());
    profile.setSex(profileDto.getSex());
    return profile;

  }

}
