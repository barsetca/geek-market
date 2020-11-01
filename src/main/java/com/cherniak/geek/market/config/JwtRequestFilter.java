package com.cherniak.geek.market.config;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

  //  private final UserDetailsService userDetailsService;
  private final JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {

    String jwt = jwtTokenUtil.getJwt(httpServletRequest);
    String username = null;
    if (jwt != null) {
      try {
        username = jwtTokenUtil.getUsernameFromToken(jwt);
      } catch (ExpiredJwtException e) {
        log.debug("The token is expired");
//                String error = JsonUtils.convertObjectToJson(new BookServiceError(HttpStatus.UNAUTHORIZED.value(), "Jwt is expired"));
//                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, error);
//                return;
      }
    }

    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(username,
          null, jwtTokenUtil.getRoles(jwt).stream().map(SimpleGrantedAuthority::new)
          .collect(Collectors.toList()));
      SecurityContextHolder.getContext().setAuthentication(token);

//            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(token);
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}

