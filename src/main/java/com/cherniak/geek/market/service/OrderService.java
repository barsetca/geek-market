package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.repository.specification.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> findAll() {
        return orderRepository.findAll(Sort.by(Sort.Order.desc("id")));
    }

    @Transactional
    public Order save(Order order) {
        return orderRepository.save(order);
    }

}
