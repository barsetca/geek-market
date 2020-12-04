package com.cherniak.geek.market.util;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest()
class CartTest {

  @MockBean
  private ProductService productService;

  @Autowired
  private Cart cart;

  @BeforeEach
  void setUp() {
    List<Product> products = new ArrayList<>(3);
    for (int i = 0; i < 3; i++) {
      products.add(new Product((long) i, "Product" + i, 100));
    }
    Mockito.doReturn(Optional.of(products.get(0)))
        .when(productService).findById(1L);
    Mockito.doReturn(Optional.of(products.get(1)))
        .when(productService).findById(2L);
    Mockito.doReturn(Optional.of(products.get(2)))
        .when(productService).findById(3L);
  }

  @Test
  void cartFillingTest() {
    for (int i = 1; i < 4; i++) {
      cart.addOrIncrement((long) i);
    }
    Assertions.assertAll(() -> {
          Assertions.assertEquals(3, cart.getItems().size());
        },
        () -> {
          Assertions.assertEquals(300, cart.getPrice());
        },
        () -> {
          Assertions.assertEquals(3, cart.getTotalQuantity());
        }
    );
  }
}