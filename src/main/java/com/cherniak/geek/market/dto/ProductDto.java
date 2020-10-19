package com.cherniak.geek.market.dto;

import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Product;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private Integer cost;
    private String categoryTitle;

    public ProductDto(Product product) {
        this.id = product.getId();
        this.title = product.getTitle();
        this.cost = product.getCost();
        this.categoryTitle = product.getCategory().getTitle();
    }

    public static Product fromDto(ProductDto productDto, Category category){
        Product product = new Product();
        product.setId(productDto.getId());
        product.setTitle(productDto.getTitle());
        product.setCost(productDto.getCost());
        product.setCategory(category);
        return product;
    }
}
