package com.cherniak.geek.market.repository;

import com.cherniak.geek.market.dto.OrderDto;
import com.cherniak.geek.market.dto.OrderItemDto;
import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Order;
import com.cherniak.geek.market.model.OrderItem;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.model.User;
import com.cherniak.geek.market.util.Cart;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class OrderRepositoryTest {

  private OrderRepository repository;

  @Autowired
  public OrderRepositoryTest(OrderRepository repository) {
    this.repository = repository;

  }

  private OrderDto orderDto;

  @BeforeEach
  void setUp() {
    orderDto = new OrderDto(1L, LocalDate.of(2020, 11, 20),
        300, "receiver", "1234567", "address");
    OrderItemDto orderItemDto1 = new OrderItemDto(1L, "Product1",
        1, 100, 100);
    OrderItemDto orderItemDto2 = new OrderItemDto(2L, "Product2",
        1, 100, 100);
    OrderItemDto orderItemDto3 = new OrderItemDto(3L, "Product3",
        1, 100, 100);
    List<OrderItemDto> orderDtoList = new ArrayList<>(
        Arrays.asList(orderItemDto1, orderItemDto2, orderItemDto3));
    orderDto.setItems(orderDtoList);

  }

  @Test
  void findAllByUserId() {
    List<Order> orders = repository.findAllByUserId(1L, Sort.by(Sort.Order.desc("id")));
    OrderDto orderDtoActual = new OrderDto(orders.get(0));
    Assertions.assertEquals(1, orders.size());
    Assertions.assertEquals(orderDto, orderDtoActual);
  }

  @Test
  void findById() {
    Order order = repository.findById(1L).get();
    Assertions.assertEquals(orderDto, new OrderDto(order));
  }

  @Test
  void save() {
    User user = new User();
    user.setId(1L);
    user.setUsername("username");
    user.setEmail("user@username.me");
    user.setPassword("password");
    Category category = new Category(1L, "Category1");
    Product product1 = new Product(1L, "Product1", 100);
    product1.setCategory(category);
    OrderItem orderItem = new OrderItem(product1);
    Cart cart = new Cart();
    cart.setItems(new ArrayList<>(Arrays.asList(orderItem)));
    cart.recalculate();
    Order order = new Order(user, cart, "receiver", "1234567", "address");
    Order orderActual = repository.save(order);
    OrderDto orderDtoActual = new OrderDto(orderActual);
    Assertions.assertAll(
        () -> {
          Assertions.assertEquals("receiver", orderDtoActual.getReceiver());
        },
        () -> {
          Assertions.assertEquals("1234567", orderDtoActual.getPhone());
        },
        () -> {
          Assertions.assertEquals("address", orderDtoActual.getAddress());
        },
        () -> {
          Assertions.assertEquals(LocalDate.now(), orderDtoActual.getDate());
        },
        () -> {
          Assertions.assertEquals(100, orderDtoActual.getCost());
        },
        () -> {
          Assertions.assertEquals(1, orderDtoActual.getItems().size());
        }
    );
  }
}