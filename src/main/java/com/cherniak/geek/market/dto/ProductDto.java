package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Product;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

  private Long id;

  @Size(min = 2, max = 1000)
  private String title;

  @Min(1)
  private Integer cost;

  @Size(min = 1, max = 100)
  private String categoryTitle;

  private boolean present;

  public ProductDto(Product product) {
    this.id = product.getId();
    this.title = product.getTitle();
    this.cost = product.getCost();
    this.categoryTitle = product.getCategory().getTitle();
    this.present = product.isPresent();
  }

  public static Product fromDto(ProductDto productDto, Category category) {
    Product product = new Product();
    product.setId(productDto.getId());
    product.setTitle(productDto.getTitle());
    product.setCost(productDto.getCost());
    product.setCategory(category);
    return product;
  }
}
