package com.cherniak.geek.market.contoller;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.service.CategoryService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class CategoryControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CategoryService service;

   private List<Category> categoryList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    categoryList.add(new Category(1L, "Category1"));
    categoryList.add(new Category(2L, "Category2"));
    categoryList.add(new Category(3L, "Category3"));
    Mockito.doReturn(categoryList)
        .when(service).findAll();
  }

  @Test
  @WithMockUser(username = "user", authorities = "USER")
  void findAll() throws Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/categories")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$").isArray())
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].title", is(categoryList.get(0).getTitle())))
        .andExpect(jsonPath("$[1].title", is(categoryList.get(1).getTitle())))
        .andExpect(jsonPath("$[2].title", is(categoryList.get(2).getTitle())));
  }
}