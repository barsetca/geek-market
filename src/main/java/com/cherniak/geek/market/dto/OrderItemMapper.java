package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.OrderItem;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderItemMapper {

    OrderItemMapper MAPPER = Mappers.getMapper(OrderItemMapper.class);

    OrderItem toOrderItem(OrderItemDto orderItemDto);

    @InheritInverseConfiguration
    OrderItemDto fromOrderItem(OrderItem orderItem);

    List<OrderItem> toOrderItemList(List<OrderItemDto> orderItemDtos);

    List<OrderItemDto> fromOrderItemList(List<OrderItem> orderItems);
}
