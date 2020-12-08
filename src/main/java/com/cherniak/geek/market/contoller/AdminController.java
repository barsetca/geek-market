package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.ProductDto;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.CategoryService;
import com.cherniak.geek.market.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

  private final ProductService productService;
  private final CategoryService categoryService;

  @PostMapping(value = "/products", consumes = "application/json", produces = "application/json")
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
    checkUserParameters(productDto);
    String categoryTitle = productDto.getCategoryTitle();
    Category category = categoryService.findByTitle(categoryTitle).orElseThrow(() ->
        new ResourceCreationException(
            String.format("Category with title %s not exists", categoryTitle)));
    Product product = ProductDto.fromDto(productDto, category);
    productService.save(product);
    productDto.setId(product.getId());

    return productDto;
  }

  @DeleteMapping("/products/{product_id}")
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void deleteProductById(@PathVariable(name = "product_id") Long productId) {
    productService.deleteById(productId);
  }

  private void checkUserParameters(ProductDto productDto) {
    Long id = productDto.getId();
    if (id != null && productService.existsById(id)) {
      throw new ResourceCreationException(String.format("Product with id = %d already exists", id));
    }
    String title = productDto.getTitle();
    if (productService.existsByTitle(title)) {
      throw new ResourceCreationException(
          String.format("Product with title %s already exists", title));
    }
  }
}
