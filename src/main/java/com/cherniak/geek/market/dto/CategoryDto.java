package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import lombok.NoArgsConstructor;

//@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto implements Serializable {

  private Long id;
  private String title;

  public CategoryDto(Category category) {
    this.id = category.getId();
    this.title = category.getTitle();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
