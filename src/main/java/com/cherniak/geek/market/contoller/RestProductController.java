package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.ProductDto;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.CategoryService;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.ProductFilter;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class RestProductController {

    ProductService productService;
   CategoryService categoryService;


    @GetMapping
    public Page<ProductDto> getAll(@RequestParam(defaultValue = "1", name = "page") Integer page,
                                   @RequestParam Map<String, String> params) {
        page = page < 1 ? 1 : page;
        ProductFilter productFilter = new ProductFilter(params);
        Page<Product> products2 = productService.findAll(productFilter.getSpec(), page - 1, 5);
        List<ProductDto> productDtos = products2.getContent().stream().map(ProductDto::new).collect(Collectors.toList());

        List<Product> products = productService.findAll(productFilter.getSpec());
        //List<ProductDto> productDtos = products.stream().map(ProductDto::new).collect(Collectors.toList());

        Pageable pageable = PageRequest.of(page - 1, 5);

        Page<ProductDto> productDtosPage = new PageImpl<>(productDtos, pageable, products2.getTotalElements());


        return productDtosPage;
        //productService.findAll(productFilter.getSpec(), page - 1, 5);
    }


    @GetMapping("/{id}")
    public Product getById(@PathVariable Long id) {
        return productService.findById(id).get();
    }

    @PostMapping
    public ProductDto create( @RequestBody ProductDto productDto, BindingResult result) {
          //  @RequestBody @Validated Product product, BindingResult result) {

        System.out.println(productDto);
        if (result.hasErrors()) {
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
        Long id = productDto.getId();

        if (id != null && productService.existsById(id)) {
            throw new ResourceCreationException(String.format("Product with id = %d already exists", id));
        }
//        String title = product.getTitle();
//        if (productService.existsByTitle(product.getTitle())) {
//            throw new ResourceCreationException(String.format("Product with title %s already exists", title));
//        }
        //product.setId(null);
        Category category = categoryService.findByTitle(productDto.getCategoryTitle()).orElseThrow(()->
                new ResourceCreationException("Category with title = " + productDto.getCategoryTitle() + "not exists"));
        Product product = ProductDto.fromDto(productDto, category);
        String title = product.getTitle();
        if (productService.existsByTitle(product.getTitle())) {
            throw new ResourceCreationException(String.format("Product with title %s already exists", title));
        }
       //System.out.println(product);
        productService.save(product);
        productDto.setId(product.getId());
        return productDto;
    }

    @PutMapping
    public void update(@RequestBody @Validated Product product, BindingResult result) {
        if (result.hasErrors()) {
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
