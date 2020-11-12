package com.cherniak.geek.market.repository;

import com.cherniak.geek.market.model.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUserId(Long userId, Sort sort);

  @Query("SELECT o FROM Order o where o.user.username=:username")
  List<Order> findAllByUsername(String username);

}
