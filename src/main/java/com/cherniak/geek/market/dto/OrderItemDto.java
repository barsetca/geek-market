package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemDto {

    private int cost;
    private int totalCost;
    private int quantity;
    private Product product;

}
