package com.cherniak.geek.market.repository;

import com.cherniak.geek.market.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {


    List<Product> findAllByTitle(String product);

    @Query("SELECT p FROM Product p WHERE p. cost = (SELECT MIN (p.cost) FROM Product p)")
    List<Product> findAllByCostIsMin();

    @Query("SELECT p FROM Product p WHERE p. cost = (SELECT MAX (p.cost) FROM Product p)")
    List<Product> findAllByCostIsMax();

    @Query("SELECT p FROM Product p WHERE p. cost = (SELECT MIN (p.cost) FROM Product p) " +
            "OR p.cost = (SELECT MAX (p.cost) FROM Product p) ORDER BY p.cost")
    List<Product> findAllByCostIsMinMax();

}
