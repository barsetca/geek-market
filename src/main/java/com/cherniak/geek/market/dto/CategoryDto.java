package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Category;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CategoryDto implements Serializable {

  private Long id;

  @NotBlank
  @Size(min = 1, max = 100)
  private String title;

  public CategoryDto(Category category) {
    this.id = category.getId();
    this.title = category.getTitle();
  }
}
