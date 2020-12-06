package com.cherniak.geek.market.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity //(debug = true)
@RequiredArgsConstructor
@Profile("!test")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final JwtRequestFilter jwtRequestFilter;

  @Override
  protected void configure(HttpSecurity http) throws Exception {

    http.authorizeRequests()
        .antMatchers("/api/v1/admin/**").hasAnyRole("ADMIN")
        .antMatchers("/api/v1/**").authenticated()
        .antMatchers("/h2-console/**").permitAll()
        .anyRequest().permitAll()
        .and()
        .csrf().disable()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    http.headers().frameOptions().disable();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
