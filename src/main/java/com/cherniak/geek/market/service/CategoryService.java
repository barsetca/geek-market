package com.cherniak.geek.market.service;

import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.repository.CategoryRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {

  private CategoryRepository categoryRepository;

  @Autowired
  public void setCategoryRepository(CategoryRepository categoryRepository) {
    this.categoryRepository = categoryRepository;
  }

  public List<Category> findAll() {
    return categoryRepository.findAll();
  }

  public Optional<Category> findByTitle(String title) {
    return categoryRepository.findByTitle(title);
  }

  @Transactional
  public Category save(Category category) {
    return categoryRepository.save(category);
  }

  public boolean existsByTitle(String title) {
    return categoryRepository.existsByTitle(title);
  }
}
