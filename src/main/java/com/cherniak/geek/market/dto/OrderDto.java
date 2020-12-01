package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Order;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OrderDto {

  private Long orderId;
  private LocalDate date;
  private int cost;
  private String receiver;
  private String phone;
  private String address;
  private List<OrderItemDto> items;

  public OrderDto(Order order) {
    this.orderId = order.getId();
    this.date = order.getDate();
    this.cost = order.getCost();
    this.receiver = order.getReceiver();
    this.phone = order.getPhone();
    this.address = order.getAddress();
    this.items = order.getItems().stream().map(OrderItemDto::new).collect(
        Collectors.toList());
  }

  public OrderDto(Long orderId, LocalDate date, int cost, String receiver, String phone,
      String address) {
    this.orderId = orderId;
    this.date = date;
    this.cost = cost;
    this.receiver = receiver;
    this.phone = phone;
    this.address = address;
  }
}
