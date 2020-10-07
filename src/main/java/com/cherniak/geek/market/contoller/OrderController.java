package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.service.OrderService;
import com.cherniak.geek.market.service.UserService;
import com.cherniak.geek.market.util.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    OrderService orderService;
    UserService userService;

    Cart cart;

    @GetMapping
    public String findAll(Model model) {
        List<Order> orders = orderService.findAll();
        model.addAttribute("orders", orders);
        return "orders";
    }

    @GetMapping("/order")
    public String doOrder() {
        return "order";
    }

    @PostMapping("/add")
    public String save(@RequestParam Map<String,String> params) {
        System.out.println(params);
        String username = params.get("username");
        User user = userService.getByUsername(username).orElseThrow(() -> new ResourceNotFoundException(String.format("User by username %s not exists", username)));
        Order order = new Order(user, cart, params.get("phone"), params.get("address"));
        orderService.save(order);
        return "redirect:/orders";
    }
}
