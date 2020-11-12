package com.cherniak.geek.market;

import com.cherniak.geek.market.model.Product;
import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.ws.GetProductsResponse;
import com.cherniak.geek.market.ws.ProductMapper;
import com.cherniak.geek.market.ws.ProductWs;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class ProductEndpoint {

  public static final String NAMESPACE_URI = "http://www.geekbrains.com/cherniak/geek/market/ws";

  private ProductService productService;

  @Autowired
  public ProductEndpoint(ProductService productService) {
    this.productService = productService;
  }

  @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getProductsRequest")
  @ResponsePayload
  public GetProductsResponse getProducts() {
    GetProductsResponse productsResponse = new GetProductsResponse();
    List<Product> productList = productService.findAll();
    List<ProductWs> products = ProductMapper.MAPPER.fromProductList(productList);
//        List<ProductWs> products = productService.findAll().stream()
//        .map(ProductAdapter::requestProduct)
//        .collect(Collectors.toList());

    productsResponse.getProducts().addAll(products);
    return productsResponse;
  }

}
