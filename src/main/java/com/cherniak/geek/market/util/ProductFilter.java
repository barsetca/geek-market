package com.cherniak.geek.market.util;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.repository.specification.ProductSpecification;
import java.util.Map;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

@Getter
public class ProductFilter {

  private Specification<Product> spec;

  public ProductFilter(Map<String, String> params) {
    spec = Specification.where(null);

    String size = params.get("size");
    if (params.containsKey("size") && !size.isBlank()) {
    }
    String minCost = params.get("min_cost");
    if (params.containsKey("min_cost") && !minCost.isBlank()) {
      int min = Integer.parseInt(minCost);
      spec = spec.and(ProductSpecification.costGreaterOrEqualsThan(min));
    }
    String maxCost = params.get("max_cost");
    if (params.containsKey("max_cost") && !maxCost.isBlank()) {
      int max = Integer.parseInt(maxCost);
      spec = spec.and(ProductSpecification.costLessOrEqualsThan(max));
    }
    String titlePart = params.get("title");
    if (params.containsKey("title") && !titlePart.isBlank()) {
      spec = spec.and(ProductSpecification.titleLike(titlePart));
    }

    String categoriesId = params.get("categoriesId");
    if (params.containsKey("categoriesId") && !categoriesId.isBlank()) {
      String[] categoryIdArray = categoriesId.trim().split(" ");

      Specification<Product> specCategories = Specification.where(null);
      for (String categoryId : categoryIdArray) {
        specCategories = specCategories
            .or(ProductSpecification.categoryIdEquals(Long.parseLong(categoryId)));
      }
      spec = spec.and(specCategories);
    }
  }
}
