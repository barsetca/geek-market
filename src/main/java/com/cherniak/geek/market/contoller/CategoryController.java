package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.CategoryDto;
import com.cherniak.geek.market.dto.CategoryListContainer;
import com.cherniak.geek.market.exception.ResourceCreationException;
import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.service.CategoryService;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private CategoryService categoryService;

  @GetMapping(produces = "application/json")
  public List<CategoryDto> findAll() {
    return categoryService.findAll().stream().map(CategoryDto::new).collect(Collectors.toList());
  }

  @GetMapping("/list")
  public CategoryListContainer findObjectListContainer() {
    List<CategoryDto> categoryDtos = categoryService.findAll().stream().map(CategoryDto::new)
        .collect(Collectors.toList());
    CategoryListContainer list = new CategoryListContainer(categoryDtos);
    return list;
  }

  @Secured("ROLE_ADMIN")
  @PostMapping(consumes = "application/json", produces = "application/json")
  public CategoryDto create(@RequestBody @Validated CategoryDto categoryDto, BindingResult result) {
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
    String title = categoryDto.getTitle().trim();
    if (categoryService.existsByTitle(title)) {
      throw new ResourceCreationException("Category with title: " + title + " already exists");
    }
    Category category = new Category();
    category.setTitle(categoryDto.getTitle());
    category.setId(null);
    category.setProducts(new ArrayList<>());
    return new CategoryDto(categoryService.save(category));
  }
}
