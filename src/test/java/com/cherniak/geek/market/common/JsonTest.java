package com.cherniak.geek.market.common;

import static org.assertj.core.api.Assertions.assertThat;

import com.cherniak.geek.market.dto.CategoryDto;
import java.io.IOException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;


@org.springframework.boot.test.autoconfigure.json.JsonTest
public class JsonTest {

  @Autowired
  private JacksonTester<CategoryDto> jackson;

  private CategoryDto categoryDto;

  private static final String TITLE = "Category1";

  @BeforeEach
  void setUp() {
    categoryDto = new CategoryDto();
    categoryDto.setId(1L);
    categoryDto.setTitle(TITLE);

  }

  @Test
  void jacksonWriteToJson() throws IOException {
    Assertions.assertAll(() -> {
          assertThat(this.jackson.write(categoryDto)).hasJsonPathNumberValue("$.id");
        },
        () -> {
          assertThat(this.jackson.write(categoryDto)).extractingJsonPathStringValue("$.title")
              .isEqualTo(TITLE);
        });
  }

  @Test
  void jacksonReadToObject() throws IOException {
    String jsonCategoryDto = this.jackson.write(categoryDto).getJson();
    Assertions.assertAll(() -> {
          assertThat(jackson.parse(jsonCategoryDto)).isEqualTo(categoryDto);
        },
        () -> {
          assertThat(this.jackson.parseObject(jsonCategoryDto).getTitle()).isEqualTo(TITLE);
        });
  }
}
