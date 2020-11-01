package com.cherniak.geek.market.contoller;

import com.cherniak.geek.market.dto.CategoryDto;
import com.cherniak.geek.market.service.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
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
}
