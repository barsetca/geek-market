package com.cherniak.geek.market.util;

import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.OrderItem;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
public class Cart {

  private ProductService productService;
  private List<OrderItem> items;
  private int price;
  private int totalQuantity;

  @PostConstruct
  public void init() {
    items = new ArrayList<>();
  }

  public void addOrIncrement(Long productId) {
    for (OrderItem item : items) {
      if (item.getProduct().getId().equals(productId)) {
        item.incrementQuantity();
        recalculate();
        return;
      }
    }
    Product p = productService.findById(productId).orElseThrow(() ->
        new ResourceNotFoundException(
            "Unable to find product with id: " + productId + " (add to cart)"));
    items.add(new OrderItem(p));
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
    items = items.stream().filter(io -> !io.getProduct().getId().equals(productId))
        .collect(Collectors.toList());
    recalculate();
  }

  public void recalculate() {
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
