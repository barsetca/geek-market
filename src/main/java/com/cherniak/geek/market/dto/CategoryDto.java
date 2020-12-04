package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto implements Serializable {

  private Long id;
  private String title;

  public CategoryDto(Category category) {
    this.id = category.getId();
    this.title = category.getTitle();
  }
}
