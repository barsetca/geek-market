package com.cherniak.geek.market.dto;


import com.cherniak.geek.market.util.Cart;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
public class CartDto {
    private List<OrderItemDto> items;
    private int price;
    private int totalQuantity;

    public CartDto(Cart cart) {
        this.price = cart.getPrice();
        this.totalQuantity = cart.getTotalQuantity();
        this.items = cart.getItems().stream().map(OrderItemDto::new).collect(Collectors.toList());
    }
}
