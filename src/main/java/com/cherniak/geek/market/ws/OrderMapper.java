package com.cherniak.geek.market.ws;

import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.model.Product;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface OrderMapper {

  OrderMapper MAPPER = Mappers.getMapper(OrderMapper.class);

  @Mapping(source = "orderId", target = "id")
  @Mapping(source = "username", target = "user.username")
  Order toOrder(OrderWs orderWs);

  List<Order> toProductList(List<OrderWs> orderWsList);

  @InheritInverseConfiguration
  OrderWs fromOrder(Order order);

  List<OrderWs> fromOrderList(List<Order> ordersList);
}
