package com.cherniak.geek.market.util;

import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Data

public class OrderItemTO {

    private int cost;

    private int totalCost;

    private int quantity;

    private String title;

}
