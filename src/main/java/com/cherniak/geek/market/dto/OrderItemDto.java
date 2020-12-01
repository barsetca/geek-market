package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDto {

  private Long productId;
  private String productTitle;
  private int quantity;
  private int cost;
  private int totalCost;

  public OrderItemDto(OrderItem o) {
    this.productId = o.getProduct().getId();
    this.productTitle = o.getProduct().getTitle();
    this.quantity = o.getQuantity();
    this.cost = o.getCost();
    this.totalCost = o.getTotalCost();
  }
}
