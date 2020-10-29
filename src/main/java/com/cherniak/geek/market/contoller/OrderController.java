package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.config.JwtTokenUtil;
import com.cherniak.geek.market.dto.OrderDto;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.OrderService;
import com.cherniak.geek.market.service.UserService;
import com.cherniak.geek.market.util.Cart;
import io.jsonwebtoken.ExpiredJwtException;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

  private final OrderService orderService;
  private final Cart cart;
  private final UserService userService;
  private final JwtTokenUtil jwtTokenUtil;

  @GetMapping
  public List<OrderDto> findAllByUserId(HttpServletRequest request) {

    String username = getUsernameFromRequest(request);
    User user = userService.getByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException(String.format("User by username %s not exists", username)));
    System.out.println("findAllByUserId" + user);
    List<OrderDto> orderDtos = orderService.findAllByUserId(user.getId()).stream()
        .map(OrderDto::new).collect(
            Collectors.toList());
    return orderDtos;
  }

  @PostMapping
  //@Transactional
  public void save(HttpServletRequest request, @RequestBody Order order) {
    if (order.getReceiver() == null || order.getPhone() == null || order.getAddress() == null) {
      throw new ResourceCreationException("Недостаточно данных для создания заказа");
    }
    String username = getUsernameFromRequest(request);
    User user = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
        String.format("User by username %s not exists", username)));
    System.out.println("user" + user);
    user.getRoles().forEach(r -> System.out.println(r));
    Order forSave = new Order(user, cart, order.getReceiver(), order.getPhone(),
        order.getAddress());
    orderService.save(forSave);
    cart.clear();

  }

  private String getUsernameFromRequest(HttpServletRequest request) {
    String usernameFromToken = null;
    String jwt = jwtTokenUtil.getJwt(request);
    if (jwt != null) {
      try {
        usernameFromToken = jwtTokenUtil.getUsernameFromToken(jwt);
      } catch (ExpiredJwtException e) {
        log.debug("The token is expired");
      }
    }
    return usernameFromToken;
  }

}
