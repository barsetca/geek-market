package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.ProductDto;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.exception.ResourceNotFoundException;
import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.CategoryService;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.util.ProductFilter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
@AllArgsConstructor
public class ProductController {

  ProductService productService;
  CategoryService categoryService;

  @GetMapping(produces = "application/json")
  public Page<ProductDto> getAll(@RequestParam(defaultValue = "1", name = "page") Integer page,
      @RequestParam Map<String, String> params,
      @RequestParam(required = false) String[] categoriesId) {
    page = page < 1 ? 1 : page;

    if (categoriesId != null && categoriesId.length != 0) {
      StringBuilder sb = new StringBuilder();
      for (String categoryId : categoriesId) {
        sb.append(categoryId).append(" ");
      }
      params.put("categoriesId", sb.toString());
    }

    ProductFilter productFilter = new ProductFilter(params);
    Page<Product> products = productService.findAll(productFilter.getSpec(), page - 1, 5);
    List<ProductDto> productDtos = products.getContent().stream().map(ProductDto::new)
        .collect(Collectors.toList());
    Pageable pageable = PageRequest.of(page - 1, 5);

    return new PageImpl<>(productDtos, pageable, products.getTotalElements());
  }

  @GetMapping(value = "/{id}", produces = "application/json")
  public ProductDto getById(@PathVariable Long id) {
    Product product = productService.findById(id).orElseThrow(
        () -> new ResourceNotFoundException(String.format("Product with id = %d not exists", id)));
    ProductDto productDto = new ProductDto(product);
    return productDto;
  }

  @PostMapping(consumes = "application/json", produces = "application/json")
  public ProductDto create(@RequestBody @Validated ProductDto productDto, BindingResult result) {
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
    String title = productDto.getTitle();
    if (productService.existsByTitle(title)) {
      throw new ResourceCreationException(
          String.format("Product with title %s already exists", title));
    }
    String categoryTitle = productDto.getCategoryTitle();
    Category category = categoryService.findByTitle(categoryTitle).orElseThrow(() ->
        new ResourceCreationException(
            String.format("Category with title %s not exists", categoryTitle)));
    Product product = ProductDto.fromDto(productDto, category);
    productService.save(product);
    productDto.setId(product.getId());

    return productDto;
  }

  @PutMapping(consumes = "application/json", produces = "application/json")
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
