package com.cherniak.geek.market.dto;


import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;


@Data
@NoArgsConstructor
public class PageDto {

  private long totalElements;
  private int totalPages;
  private boolean first;
  private boolean last;
  private int number;
  private int numberOfElements;
  private int size;
  List<ProductDto> content;

  public PageDto(Page<ProductDto> dtoPage) {
    this.totalElements = dtoPage.getTotalElements();
    this.totalPages = dtoPage.getTotalPages();
    this.first = dtoPage.isFirst();
    this.last = dtoPage.isLast();
    this.number = dtoPage.getNumber();
    this.numberOfElements = dtoPage.getNumberOfElements();
    this.size = dtoPage.getSize();
    this.content = dtoPage.getContent();
  }
}
