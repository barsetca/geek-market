package com.cherniak.geek.market.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.cherniak.geek.market.dto.CategoryDto;
import com.cherniak.geek.market.dto.CategoryListContainer;
import com.cherniak.geek.market.dto.ProductDto;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class ServerTest {

  @Autowired
  private TestRestTemplate restTemplate;

  @Test
    // @WithMockUser(username = "admin", authorities = "ADMIN")
  void getAllCategoriesFromContainer() {
    CategoryListContainer listContainer = restTemplate
        .getForObject("/api/v1/categories/list", CategoryListContainer.class);
    assertThat(listContainer).isNotNull();

    List<CategoryDto> categoryDtoList = new ArrayList<>(listContainer.getCategoryDtos());
    categoryDtoList.forEach(System.out::println);

    ResponseEntity<CategoryListContainer> entity = restTemplate
        .getForEntity("/api/v1/categories/list", CategoryListContainer.class);
    Assertions.assertSame(entity.getStatusCode(), HttpStatus.OK);

  }

  @Test
    // @WithMockUser(username = "admin", authorities = "ADMIN")
  void getAllCategoriesFromArray() {
    CategoryDto[] categoryDtos = restTemplate
        .getForObject("/api/v1/categories", CategoryDto[].class);
    assertThat(categoryDtos).isNotNull();

    List<CategoryDto> categoryDtoList = new ArrayList<>(Arrays.asList(categoryDtos));
    categoryDtoList.forEach(System.out::println);

    assertThat(categoryDtoList).isNotNull();
    assertThat(categoryDtoList).hasSize(3);

    ResponseEntity<CategoryDto[]> entity = restTemplate
        .getForEntity("/api/v1/categories", CategoryDto[].class);
    Assertions.assertSame(entity.getStatusCode(), HttpStatus.OK);

  }

  @Test
    //@WithMockUser(username = "admin", authorities = "ADMIN")
  void getProduct() {
    ProductDto productDto = restTemplate.getForObject("/api/v1/products/1", ProductDto.class);
    assertThat(productDto).isNotNull();

    System.out.println(productDto);

    ResponseEntity<ProductDto> entity = restTemplate
        .getForEntity("/api/v1/products/1", ProductDto.class);
    Assertions.assertSame(entity.getStatusCode(), HttpStatus.OK);
  }
}
