package com.cherniak.geek.market.repository.specification;

import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Product;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

public class ProductSpecification {

    public static Specification<Product> costGreaterOrEqualsThan(int minCost) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.greaterThanOrEqualTo(root.get("cost"), minCost);
    }

    public static Specification<Product> costLessOrEqualsThan(int maxCost) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("cost"), maxCost);
    }

    public static Specification<Product> titleLike(String titlePart) {
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.like(root.get("title"), String.format("%%%s%%", titlePart));
    }

    public static Specification<Product> categoryIdEquals(Category category) {
        System.out.println("categoryId " + category.getId());
        return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("category"), category);

    }
}
