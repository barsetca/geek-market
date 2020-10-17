package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.OrderItemDto;
import com.cherniak.geek.market.dto.OrderItemMapper;
import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.Cart;
import com.cherniak.geek.market.util.CartDto;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/api/v1/restcart")
@AllArgsConstructor
public class RestCartController {

    private ProductService productService;
    private Cart cart;

    @GetMapping
    public CartDto getCurrentCart() {
        List<OrderItemDto> orderItemDtos = OrderItemMapper.MAPPER.fromOrderItemList(cart.getItems());
        CartDto cartDto = new CartDto();
        cartDto.setPrice(cart.getPrice());
        cartDto.setTotalQuantity(cart.getTotalQuantity());
        cartDto.setItems(orderItemDtos);

        return cartDto;
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

    @DeleteMapping("/{product_id}")
    public void removeProduct(@PathVariable(name = "product_id") Long productId) {
        cart.remove(productId);
    }

    @GetMapping("/clear")
    public String clearCart() {
        cart.clear();
        return "redirect:/cart";
    }
}
