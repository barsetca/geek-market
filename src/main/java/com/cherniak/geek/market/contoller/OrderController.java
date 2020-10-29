package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.OrderService;
import com.cherniak.geek.market.service.UserService;
import com.cherniak.geek.market.util.Cart;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

  OrderService orderService;
  UserService userService;
  Cart cart;

  @GetMapping
  public String findAllByUserId(Model model, Principal principal) {
    String username = principal.getName();
    User user = userService.getByUsername(username).orElseThrow(() ->
        new UsernameNotFoundException(String.format("User by username %s not exists", username)));
    List<Order> orders = orderService.findAllByUserId(user.getId());
    model.addAttribute("orders", orders);
    model.addAttribute("username", username);
    return "orders";
  }

  @GetMapping("/order")
  public String doOrder(Principal principal, Model model) {
    model.addAttribute("username", principal.getName());
    return "order";
  }

  @PostMapping("/add")
  public String save(Principal principal, @RequestParam Map<String, String> params) {
    String username = principal.getName();
    User user = userService.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException(
        String.format("User by username %s not exists", username)));
    Order order = new Order(user, cart, params.get("receiver"), params.get("phone"),
        params.get("address"));
    orderService.save(order);
    return "redirect:/orders";
  }
}
