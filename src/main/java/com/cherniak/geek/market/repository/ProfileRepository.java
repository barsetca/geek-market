package com.cherniak.geek.market.repository;

import com.cherniak.geek.market.model.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

//@Query("SELECT p FROM Profile p where p.user.username=:username")
//   Profile findByUserUsername(@Param("username") String username);

  Profile findByUserId(Long id);

}
