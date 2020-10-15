package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.Cart;
import com.cherniak.geek.market.util.CartTO;
import com.cherniak.geek.market.util.OrderItemTO;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restcart")
@AllArgsConstructor
public class RestCartController {

    private ProductService productService;
    private Cart cart;

    @GetMapping
    public CartTO getCurrentCart() {

        List<OrderItemTO> orderItemTOS = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OrderItemTO itemTO = new OrderItemTO();
            itemTO.setCost(i);
            itemTO.setQuantity(i);
            itemTO.setTitle("title" + i);
            itemTO.setTotalCost(4);
            orderItemTOS.add(itemTO);
        }
        CartTO cartTO = new CartTO();
        cartTO.setItems(orderItemTOS);
        cartTO.setPrice(16);
        cartTO.setTotalQuantity(16);
        return cartTO;
    }

    @GetMapping("/add/{product_id}")
    public void addProductToCart(@PathVariable(name = "product_id") Long productId,
                                 HttpServletRequest request, HttpServletResponse response) {
        Product product = productService.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Продукт с id = " + productId + " не найден"));
        cart.add(product);
    }

    @GetMapping("/inc/{product_id}")
    public String incrementProduct(@PathVariable(name = "product_id") Long productId) {
        cart.increment(productId);
        return "redirect:/cart";

    }

    @GetMapping("/dec/{product_id}")
    public String removeOrDecrementProduct(@PathVariable(name = "product_id") Long productId) {
        cart.removeOrDecrement(productId);
        return "redirect:/cart";
    }

    @GetMapping("/remove/{product_id}")
    public String removeProduct(@PathVariable(name = "product_id") Long productId) {
        cart.remove(productId);
        return "redirect:/cart";
    }

    @GetMapping("/clear")
    public String clearCart() {
        cart.clear();
        return "redirect:/cart";
    }
}
