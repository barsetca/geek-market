package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.ProductFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
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
    public Product create(@RequestBody @Validated Product product, BindingResult result) {
        if(result.hasErrors()){
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(
                    fe -> {
                        String msg = fe.getDefaultMessage();
                        if (msg != null) {
                            if (!msg.startsWith(fe.getField())) {
                                msg = fe.getField() + " - " + msg;
                            }
                            sb.append(" Поле: ").append(msg);
                        }
                    });
            throw new ResourceCreationException(sb.toString());
        }
        Long id = product.getId();

        if (id !=null && productService.existsById(id)){
            throw new ResourceCreationException(String.format("Product with id = %d already exists", id));
        }
        String title = product.getTitle();
        if (productService.existsByTitle(product.getTitle())){
            throw new ResourceCreationException(String.format("Product with title %s already exists",title));
        }
        product.setId(null);
        return productService.save(product);
    }

    @PutMapping
    public void update(@RequestBody @Validated Product product, BindingResult result) {
        if(result.hasErrors()){
            StringBuilder sb = new StringBuilder();
            result.getFieldErrors().forEach(
                    fe -> {
                        String msg = fe.getDefaultMessage();
                        if (msg != null) {
                            if (!msg.startsWith(fe.getField())) {
                                msg = fe.getField() + " - " + msg;
                            }
                            sb.append(" Поле: ").append(msg);
                        }
                    });
            throw new ResourceCreationException(sb.toString());
        }
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
