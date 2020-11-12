package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public void setProductRepository(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<Product> findById(Long id) {
        return productRepository.findById(id);
    }

    public Page<Product> findAll(Specification<Product> spec, int page, int size) {
        return productRepository.findAll(spec, PageRequest.of(page, size));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }


    @Transactional
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Transactional
    public void deleteAll() {
        productRepository.deleteAll();
    }

    @Transactional
    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }

    public boolean existsById(Long id){
        return productRepository.existsById(id);
    }

    public boolean existsByTitle(String title){
        return productRepository.existsByTitle(title);
    }

}
