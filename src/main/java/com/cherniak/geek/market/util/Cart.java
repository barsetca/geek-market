package com.cherniak.geek.market.util;

import com.cherniak.geek.market.model.Order;
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
import java.util.List;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@NoArgsConstructor
@Data
public class Cart {
    private List<OrderItem> items;
    private int price;

    @PostConstruct
    public void init(){
        items = new ArrayList<>();
    }

    public void add (Product product){
        for(OrderItem item : items){
            if(item.getProduct().getId().equals(product.getId())){
                item.incrementQuantity();
                recalculate();
                return;
            }
        }

        items.add(new OrderItem(product));
        recalculate();

    }

    public void recalculate(){
        price = 0;
        for (OrderItem item : items){
            price+=item.getQuantity()*item.getCost();
        }
    }
}
