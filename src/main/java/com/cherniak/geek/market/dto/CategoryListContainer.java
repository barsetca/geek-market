package com.cherniak.geek.market.dto;

import java.util.List;
import lombok.NoArgsConstructor;


@NoArgsConstructor

public class CategoryListContainer {

  private List<CategoryDto> categoryDtos;

  public CategoryListContainer(List<CategoryDto> categoryDtos) {
    this.categoryDtos = categoryDtos;
  }

  public List<CategoryDto> getCategoryDtos() {
    return categoryDtos;
  }

  public void setCategoryDtos(List<CategoryDto> categoryDtoArray) {
    this.categoryDtos = categoryDtoArray;
  }
}
