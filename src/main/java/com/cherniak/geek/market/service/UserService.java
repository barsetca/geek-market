package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.Profile;
import com.cherniak.geek.market.model.Role;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.repository.UserRepository;

import java.util.*;
import java.util.stream.Collectors;
import javax.management.relation.RoleNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final RoleService roleService;

  public Optional<User> getByUsername(String username) {
    return userRepository.findByUsername(username);
  }

  public Optional<User> getById(Long id) {
    return userRepository.findById(id);
  }

  public boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Transactional
  public User save(User user) {
    return userRepository.save(user);
  }

  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Transactional
  public User create(User user) throws RoleNotFoundException {
    Set<Role> roles = new HashSet<>();
    Role role = roleService.findByName("ROLE_USER").orElseThrow(() ->
        new RoleNotFoundException("ROLE_USER not found"));
    roles.add(role);
    user.setRoles(roles);
    Profile profile = new Profile();
    profile.setUser(user);
    user.setProfile(profile);
    return save(user);
  }

  @Override
  @Transactional
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
        String.format("User by username %s not exists", username)));
    return new org.springframework.security.core.userdetails.User(user.getUsername(),
        user.getPassword(), mapRolesToAuthorities(user.getRoles()));
  }

  private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
    List<GrantedAuthority> authorities = new ArrayList<>();
    for (Role role: roles) {
      authorities.add(new SimpleGrantedAuthority(role.getName()));
      role.getAuthorities().stream()
              .map(p -> new SimpleGrantedAuthority(p.getName()))
              .forEach(authorities::add);
    }
    return authorities;
  }
}
