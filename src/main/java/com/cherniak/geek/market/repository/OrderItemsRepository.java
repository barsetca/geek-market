package com.cherniak.geek.market.repository;

import com.cherniak.geek.market.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemsRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findAllByOrderId(Long id);
}
