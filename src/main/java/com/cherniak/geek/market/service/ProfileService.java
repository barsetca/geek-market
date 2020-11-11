package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.Profile;
import com.cherniak.geek.market.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {

  private ProfileRepository profileRepository;

  @Autowired
  public void setProfileRepository(ProfileRepository profileRepository) {
    this.profileRepository = profileRepository;
  }

//  public Profile findByUserUsername(String username) {
//    return profileRepository.findByUserUsername(username);
//  }

  public Profile findByUserId(Long userId) {
    return profileRepository.findByUserId(userId);
  }


}
