package com.cherniak.geek.market.contoller;


import com.cherniak.geek.market.dto.OrderItemDto;
import com.cherniak.geek.market.model.OrderItem;
import com.cherniak.geek.market.service.OrderItemsService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order_items")
public class RestOrderItemController {

  OrderItemsService orderItemsService;

  @GetMapping("/{product_id}")
  public List<OrderItemDto> findAllByProductId(@PathVariable(name = "product_id") Long id) {
    return orderItemsService.findAllByOrderId(id).stream().map(oi -> new OrderItemDto(oi)).collect(
        Collectors.toList());
  }


}
