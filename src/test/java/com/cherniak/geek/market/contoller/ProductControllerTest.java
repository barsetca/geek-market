package com.cherniak.geek.market.contoller;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.cherniak.geek.market.dto.ProductDto;
import com.cherniak.geek.market.model.Category;
import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.CategoryService;
import com.cherniak.geek.market.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private ProductService service;

  @MockBean
  private CategoryService categoryService;

  private Category category;
  private Product product;

  private static final Long ID = 4L;
  private static final String CATEGORY_TITLE = "Category4";
  private static final String PRODUCT_TITLE = "Product4";

  @BeforeEach
  void setUp() {
    category = new Category();
    category.setId(ID);
    category.setTitle(CATEGORY_TITLE);
    product = new Product();
    product.setId(ID);
    product.setTitle(PRODUCT_TITLE);
    product.setCost(100);
    product.setCategory(category);
  }

  @Test
  @WithMockUser(username = "user", authorities = "USER")
  void getAll() throws Exception {
    List<Product> productList = new ArrayList<>(Arrays.asList(product));
    Pageable pageable = PageRequest.of(0, 5);
    Page<Product> productPage = new PageImpl<>(productList, pageable, 1);
    Mockito.doReturn(productPage)
        .when(service).findAll(Specification.where(null), 0, 5);
    Map<String, String> params = new HashMap<>();
    params.put("page", "1");
    params.put("size", "");
    params.put("min_cost", "");
    params.put("max_cost", "");
    params.put("title", "");
    ProductDto productDto = new ProductDto(product);
    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products", params)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(jsonPath("$.content").isArray())
        .andExpect(jsonPath("$.content", hasSize(productList.size())))
        .andExpect(jsonPath("$.content[0].title", is(productDto.getTitle())))
        .andExpect(jsonPath("$.content[0].cost", is(productDto.getCost())))
        .andExpect(jsonPath("$.totalElements", is((int) productPage.getTotalElements())))
        .andExpect(jsonPath("$.totalPages", is(productPage.getTotalPages())))
        .andExpect(jsonPath("$.size", is(productPage.getSize())));
  }

  @Test
  @WithMockUser(username = "user", authorities = "USER")
  void getById() throws Exception {

    Mockito.doReturn(Optional.of(product))
        .when(service).findById(ID);

    mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/products/4")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.title", is(product.getTitle())))
        .andExpect(jsonPath("$.cost", is(product.getCost())))
        .andExpect(jsonPath("$.categoryTitle", is(product.getCategory().getTitle())));
  }

  @Test
  @WithMockUser(username = "admin", authorities = "ADMIN")
  void create() throws Exception {
    Mockito.doReturn(Boolean.valueOf(false))
        .when(service).existsById(ID);
    Mockito.doReturn(Boolean.valueOf(false))
        .when(service).existsByTitle(PRODUCT_TITLE);
    Mockito.doReturn(Optional.of(category))
        .when(categoryService).findByTitle(CATEGORY_TITLE);
    Mockito.doReturn(product)
        .when(service).save(product);
    Map<String, String> param = new HashMap<>();
    param.put("title", PRODUCT_TITLE);
    param.put("categoryTitle", CATEGORY_TITLE);
    param.put("cost", "100");
    StringWriter writer = new StringWriter();
    ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(writer, new ProductDto(product));
    mockMvc.perform(post("/api/v1/products")
        .contentType(MediaType.APPLICATION_JSON)
        .content(writer.toString()))
        .andExpect(status().isOk());
  }

  @Test
  void update() {
  }

  @Test
  void deleteAll() {
  }

  @Test
  void deleteById() {
  }
}