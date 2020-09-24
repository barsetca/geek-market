package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.repository.ProductRepository;
import com.cherniak.geek.market.repository.specification.ProductSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
//@Transactional()
public class ProductService {

    private ProductRepository productRepository;

    private static final Sort SORT_COST = Sort.by(Sort.Order.asc("cost"));

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Page<Product> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page, size));
    }
//
//    public List<Product> findAllMinCost() {
//        return productRepository.findAllByCostIsMin();
//    }
//
//    public List<Product> findAllMaxCost() {
//        return productRepository.findAllByCostIsMax();
//    }
//
//    public List<Product> findAllMinMaxCost() {
//        return productRepository.findAllByCostIsMinMax();
//    }
//
//    public Page<Product> findPage(int page, int size) {
//        return productRepository.findAll(PageRequest.of(page, size));
//    }



    public long getCount() {
        return productRepository.count();
    }

//    public List<Product> filterCost(int min, int max) {
//        return productRepository.getProductByCostGreaterThanEqualAndCostLessThanEqual(min, max, SORT_COST);
    }

