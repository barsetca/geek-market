package com.cherniak.geek.market.repository.specification;

import com.cherniak.geek.market.model.Product;
import org.springframework.data.jpa.domain.Specification;

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

  public static Specification<Product> categoryIdEquals(long categoryId) {
    return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.equal(root.get("category"), categoryId);
  }

  public static Specification<Product> isPresent() {
    return (Specification<Product>) (root, criteriaQuery, criteriaBuilder) ->
        criteriaBuilder.isTrue(root.get("present"));
  }
}
