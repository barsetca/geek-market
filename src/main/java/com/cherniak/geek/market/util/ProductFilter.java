package com.cherniak.geek.market.util;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.repository.specification.ProductSpecification;
import lombok.Getter;
import org.springframework.data.jpa.domain.Specification;

import java.util.Map;


@Getter
public class ProductFilter {
    private Specification<Product> spec;
    private String filterDefinition;

    public ProductFilter(Map<String, String> params) {
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

        String categoriesId = params.get("categoriesId");
        if (params.containsKey("categoriesId") && !categoriesId.isBlank()) {
            String[] categoryIdArray = categoriesId.trim().split(" ");
            filterDefinitionBuilder.append("&categoriesId=");
            Specification<Product> specCategories = Specification.where(null);
            for (String categoryId : categoryIdArray) {
                specCategories = specCategories.or(ProductSpecification.categoryIdEquals(Long.parseLong(categoryId)));
                filterDefinitionBuilder.append(categoriesId).append(" ");
            }
            spec = spec.and(specCategories);

        }
        filterDefinition = filterDefinitionBuilder.toString();
    }
}
