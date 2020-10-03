package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.service.OrderService;
import com.cherniak.geek.market.util.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    OrderService orderService;
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
    public String save(@RequestParam Map<String, String> params) {
        Order order = new Order();
        order.setFirstname(params.get("firstname"));
        order.setSurname(params.get("surname"));
        order.setPhone(params.get("phone"));
        order.setAddress(params.get("address"));
        order.setCost(cart.getPrice());
        cart.getItems().forEach(io -> io.setOrder(order));
        order.setItems(cart.getItems());
        orderService.save(order);
        cart.clear();
        return "redirect:/orders";
    }
}
