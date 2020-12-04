package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.OrderDto;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.OrderService;
import com.cherniak.geek.market.service.UserService;
import com.cherniak.geek.market.util.Cart;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.annotation.Validated;
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

  @GetMapping
  public List<OrderDto> findAllByUserId(Principal principal) {

    String username = principal.getName();
    User user = userService.getByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException(String.format("User by username %s not exists", username)));
    List<OrderDto> orderDtos = orderService.findAllByUserId(user.getId()).stream()
        .map(OrderDto::new).collect(
            Collectors.toList());
    return orderDtos;
  }

  @PostMapping
  public void save(Principal principal, @RequestBody @Validated Order order) {
    if (order.getReceiver() == null || order.getPhone() == null || order.getAddress() == null) {
      throw new ResourceCreationException("Недостаточно данных для создания заказа");
    }
    String username = principal.getName();

    User user = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
        String.format("User by username %s not exists", username)));

    orderService.save(new Order(user, cart, order.getReceiver(), order.getPhone(),
        order.getAddress()));

    cart.clear();

  }
}
