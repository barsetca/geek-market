package com.cherniak.geek.market.util;

import com.cherniak.geek.market.dto.ProductDto;
import com.cherniak.geek.market.model.Product;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
public class Utile {

    private Page<Product> products;
    private Page<ProductDto> productDtos;

    public Utile(Page<Product> products) {
        this.products = products;
        this.productDtos = new Page<ProductDto>() {
            @Override
            public int getTotalPages() {
                return products.getTotalPages();
            }

            @Override
            public long getTotalElements() {
                return products.getTotalElements();
            }

            @Override
            public <U> Page<U> map(Function<? super ProductDto, ? extends U> function) {
                return null;
            }

            @Override
            public int getNumber() {
                return products.getNumber();
            }

            @Override
            public int getSize() {
                return products.getSize();
            }

            @Override
            public int getNumberOfElements() {
                return products.getNumber();
            }

            @Override
            public List<ProductDto> getContent() {
                return products.getContent().stream().map(ProductDto::new).collect(Collectors.toList());
            }

            @Override
            public boolean hasContent() {
                return products.hasContent();
            }

            @Override
            public Sort getSort() {
                return products.getSort();
            }

            @Override
            public boolean isFirst() {
                return products.isFirst();
            }

            @Override
            public boolean isLast() {
                return products.isLast();
            }

            @Override
            public boolean hasNext() {
                return products.hasContent();
            }

            @Override
            public boolean hasPrevious() {
                return products.hasPrevious();
            }

            @Override
            public Pageable nextPageable() {
                return products.nextPageable();
            }

            @Override
            public Pageable previousPageable() {
                return products.previousPageable();
            }

            @Override
            public Iterator<ProductDto> iterator() {
                return null;
            }
        };

    }

}
