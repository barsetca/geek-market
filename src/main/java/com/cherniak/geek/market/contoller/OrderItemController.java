package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.model.OrderItem;
import com.cherniak.geek.market.repository.OrderItemsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/order_items")
public class OrderItemController {

    OrderItemsRepository orderItemsRepository;

    @GetMapping("/{product_id}")
    public String findAllByProductId(Model model, @PathVariable(name = "product_id") Long id) {
        List<OrderItem> orderItems = orderItemsRepository.findAllByOrderId(id);
        model.addAttribute("orderItems", orderItems);
        return "order_items";
    }
}
