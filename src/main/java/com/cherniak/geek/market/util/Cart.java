package com.cherniak.geek.market.util;

import com.cherniak.geek.market.model.OrderItem;
import com.cherniak.geek.market.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
@Data
public class Cart {
    private List<OrderItem> items;
    private int price;
    private int totalQuantity;

    @PostConstruct
    public void init() {
        items = new ArrayList<>();
    }

    public void add(Product product) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(product.getId())) {
                item.incrementQuantity();
                recalculate();
                return;
            }
        }
        items.add(new OrderItem(product));
        recalculate();
    }

    public void increment(Long productId) {
        for (OrderItem item : items) {
            if (item.getProduct().getId().equals(productId)) {
                item.incrementQuantity();
                recalculate();
                return;
            }
        }
        recalculate();
    }

    public void removeOrDecrement(Long productId) {
        Iterator<OrderItem> iterator = items.iterator();
        while (iterator.hasNext()) {
            OrderItem item = iterator.next();
            if (item.getProduct().getId().equals(productId)) {
                item.decrementQuantity();
                if (item.getTotalCost() == 0) {
                    iterator.remove();
                }
                recalculate();
                return;
            }
        }
    }

    public void remove(Long productId) {
        items = items.stream().filter(io -> !io.getProduct().getId().equals(productId)).collect(Collectors.toList());
        recalculate();
    }

    private void recalculate() {
        price = 0;
        totalQuantity = 0;
        for (OrderItem item : items) {
            price += item.getTotalCost();
            totalQuantity += item.getQuantity();
        }
    }

    public void clear() {
        items.clear();
        recalculate();
    }
}
