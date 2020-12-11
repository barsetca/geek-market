package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Profile;
import java.time.LocalDate;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ProfileDto {

  private Long id;
  @Size(min = 2)
  private String firstname;
  @Size(min = 2)
  private String surname;
  @Size(min = 3)
  private String phone;
  @PastOrPresent
  private LocalDate birthday;
  @Size(min = 1)
  private String sex;
  @Size(min = 2)
  private String city;
  @Email
  private String email;
  @NotBlank
  @Size(min = 2)
  private String username;
  private Long userId;
  private boolean enabled;

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
    this.enabled = profile.getUser().isEnabled();
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
