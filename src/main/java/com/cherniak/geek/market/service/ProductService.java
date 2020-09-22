package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional()
public class ProductService {

    private ProductRepository productRepository;

    private static final Sort SORT_COST = Sort.by(Sort.Order.asc("cost"));

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll(SORT_COST);
    }

    public List<Product> findAllMinCost() {
        return productRepository.findAllByCostIsMin();
    }

    public List<Product> findAllMaxCost() {
        return productRepository.findAllByCostIsMax();
    }

    public List<Product> findAllMinMaxCost() {
        return productRepository.findAllByCostIsMinMax();
    }

    public Page<Product> findPage(int page, int size) {
        return productRepository.findAll(PageRequest.of(page, size));
    }

    public long getCount() {
        return productRepository.count();
    }

    public List<Product> filterCost(int min, int max) {
        return productRepository.getProductByCostGreaterThanEqualAndCostLessThanEqual(min, max, SORT_COST);
    }
}
