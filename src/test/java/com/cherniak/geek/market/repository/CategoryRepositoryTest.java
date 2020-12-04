package com.cherniak.geek.market.repository;

import com.cherniak.geek.market.dto.CategoryDto;
import com.cherniak.geek.market.model.Category;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
class CategoryRepositoryTest {


  private CategoryRepository repository;


  @Autowired
  public CategoryRepositoryTest(CategoryRepository repository) {
    this.repository = repository;
  }

  private final List<CategoryDto> categoryDtoList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    categoryDtoList.add(new CategoryDto(new Category(1L, "Category1")));
    categoryDtoList.add(new CategoryDto(new Category(2L, "Category2")));
    categoryDtoList.add(new CategoryDto(new Category(3L, "Category3")));
  }

  @Test
  void findByTitle() {
    Assertions.assertEquals(categoryDtoList.get(0),
        new CategoryDto(repository.findByTitle("Category1").get()));
    Assertions.assertEquals(categoryDtoList.get(1),
        new CategoryDto(repository.findByTitle("Category2").get()));
    Assertions.assertEquals(categoryDtoList.get(2),
        new CategoryDto(repository.findByTitle("Category3").get()));
  }

  @Test
  void findByAll() {
    Assertions.assertEquals(categoryDtoList, repository.findAll().stream().map(CategoryDto::new)
        .collect(Collectors.toList()));
  }
}