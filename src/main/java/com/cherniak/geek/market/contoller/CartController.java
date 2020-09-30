package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.Cart;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/cart")
@AllArgsConstructor
public class CartController {

    private ProductService productService;
    private Cart cart;

    @GetMapping
    public String showCart() {
        return "cart";
    }

    @GetMapping("/add/{product_id}")
    public void addProductToCart(@PathVariable(name = "product_id") Long productId,
                                 HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        Product product = productService.findById(productId).orElseThrow(() ->
                new ResourceNotFoundException("Продукт с id = " + productId + " не найден"));
        cart.add(product);
        response.sendRedirect(request.getHeader("referer"));
    }

}
