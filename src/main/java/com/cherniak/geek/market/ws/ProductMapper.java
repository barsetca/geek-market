package com.cherniak.geek.market.ws;

import com.cherniak.geek.market.model.Product;
import java.util.List;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

  ProductMapper MAPPER = Mappers.getMapper(ProductMapper.class);

  @Mapping(source = "productId", target = "id")
  @Mapping(source = "categoryId", target = "category.id")
  @Mapping(source = "categoryTitle", target = "category.title")
  Product toProduct(ProductWs productWs);

  List<Product> toProductList(List<ProductWs> productWsList);

  @InheritInverseConfiguration
  ProductWs fromProduct(Product product);

  List<ProductWs> fromProductList(List<Product> products);
}
