package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.OrderItem;
import com.cherniak.geek.market.repository.OrderItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderItemsService {

    private OrderItemsRepository orderItemsRepository;

    @Autowired
    public void setOrderItemsRepository(OrderItemsRepository orderItemsRepository) {
        this.orderItemsRepository = orderItemsRepository;
    }

    public List<OrderItem> findAllByOrderId(Long id) {

        return orderItemsRepository.findAllByOrderId(id);
    }
}
