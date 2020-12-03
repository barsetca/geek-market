package com.cherniak.geek.market;

import static org.assertj.core.api.Assertions.assertThat;

import com.cherniak.geek.market.dto.CategoryListContainer;
import com.cherniak.geek.market.model.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ServerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
  @WithMockUser(username = "admin", authorities = "ADMIN")
  void getAllCategories() {
    CategoryListContainer listContainer = restTemplate
        .getForObject("/api/v1/categories/list", CategoryListContainer.class);
    assertThat(listContainer).isNotNull();

  }

  @Test
  @WithMockUser(username = "admin", authorities = "ADMIN")
  void getProduct() {
    Product product = restTemplate.getForObject("/api/v1/products/1", Product.class);
    assertThat(product).isNotNull();
  }


}
