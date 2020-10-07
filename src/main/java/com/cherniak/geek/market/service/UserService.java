package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> getByUsername(String username){
        return userRepository.findByUsername(username);
    }
}
