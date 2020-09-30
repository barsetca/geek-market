package com.cherniak.geek.market.util;

import com.cherniak.geek.market.model.Product;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SimpleCart {
    private List<Product> items;

    public SimpleCart() {
        items = new ArrayList<>();
    }

    public void add(Product product) {
        items.add(product);
    }
}
