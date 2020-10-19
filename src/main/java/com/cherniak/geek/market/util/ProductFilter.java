package com.cherniak.geek.market.util;

import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.repository.specification.ProductSpecification;
import com.cherniak.geek.market.service.CategoryService;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;


@Getter
public class ProductFilter {
    private Specification<Product> spec;
    private String filterDefinition;

    public ProductFilter(Map<String, String> params, CategoryService categoryService) {
        StringBuilder filterDefinitionBuilder = new StringBuilder();
        spec = Specification.where(null);

        String size = params.get("size");
        if (params.containsKey("size") && !size.isBlank()) {
            filterDefinitionBuilder.append("&size=").append(size);
        }
        String minCost = params.get("min_cost");
        if (params.containsKey("min_cost") && !minCost.isBlank()) {
            int min = Integer.parseInt(minCost);
            spec = spec.and(ProductSpecification.costGreaterOrEqualsThan(min));
            filterDefinitionBuilder.append("&min_cost=").append(minCost);
        }
        String maxCost = params.get("max_cost");
        if (params.containsKey("max_cost") && !maxCost.isBlank()) {
            int max = Integer.parseInt(maxCost);
            spec = spec.and(ProductSpecification.costLessOrEqualsThan(max));
            filterDefinitionBuilder.append("&max_cost=").append(maxCost);
        }
        String titlePart = params.get("title");
        if (params.containsKey("title") && !titlePart.isBlank()) {
            spec = spec.and(ProductSpecification.titleLike(titlePart));
            filterDefinitionBuilder.append("&title=").append(titlePart);
        }

        String categoryTitle = params.get("categoryTitle");
        if (params.containsKey("categoryTitle") && !categoryTitle.isBlank()) {
            Category category = categoryService.findByTitle(categoryTitle).orElseThrow(() ->
                    new ResourceCreationException(String.format("Category with title %s not exists", categoryTitle)));
            spec = spec.and(ProductSpecification.categoryIdEquals(category));
            filterDefinitionBuilder.append("&categoryTitle=").append(params.get("categoryTitle"));
        }
        filterDefinition = filterDefinitionBuilder.toString();
    }
}
