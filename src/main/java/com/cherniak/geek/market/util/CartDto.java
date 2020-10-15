package com.cherniak.geek.market.util;

import com.cherniak.geek.market.dto.OrderItemDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartDto {

    private List<OrderItemDto> items;
    private int price;
    private int totalQuantity;
}
