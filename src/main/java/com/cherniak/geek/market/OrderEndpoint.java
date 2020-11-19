package com.cherniak.geek.market;

import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.service.OrderService;
import com.cherniak.geek.market.ws.GetOrdersRequest;
import com.cherniak.geek.market.ws.GetOrdersResponse;
import com.cherniak.geek.market.ws.OrderMapper;
import com.cherniak.geek.market.ws.OrderWs;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class OrderEndpoint {

  public static final String NAMESPACE_URI = "http://www.geekbrains.com/cherniak/geek/market/ws/orders";

  private OrderService orderService;

  @Autowired
  public OrderEndpoint(OrderService orderService) {
    this.orderService = orderService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getOrdersRequest")
  @ResponsePayload
  public GetOrdersResponse getOrders(@RequestPayload GetOrdersRequest request) {
    GetOrdersResponse ordersResponse = new GetOrdersResponse();
    List<Order> orderList = orderService.findAllByUsername(request.getUsername());
    List<OrderWs> orderWsList = OrderMapper.MAPPER.fromOrderList(orderList);
    ordersResponse.getOrders().addAll(orderWsList);
    return ordersResponse;
  }

}
