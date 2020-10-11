package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.ProductFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class RestProductController {

    ProductService productService;

    @GetMapping
    public Page<Product> getAll(@RequestParam(defaultValue = "1", name = "page") Integer page,
                                @RequestParam Map<String, String> params) {
        page = page < 1 ? 1 : page;
        ProductFilter productFilter = new ProductFilter(params);
        return productService.findAll(productFilter.getSpec(), page - 1, 5);
    }

    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id).get();
    }

    @PostMapping
    public Product create(@RequestBody Product product) {
        product.setId(null);
        return productService.save(product);
    }

    @PutMapping
    public void update(@RequestBody Product product) {
        productService.save(product);
    }

    @DeleteMapping
    public void deleteAll() {
        productService.deleteAll();
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        productService.deleteById(id);
    }

}
