package com.cherniak.geek.market.util;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor
@Data
public class CartTO {
    private List<OrderItemTO> items;
    private int price;
    private int totalQuantity;

}
