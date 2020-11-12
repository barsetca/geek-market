package com.cherniak.geek.market;

import com.cherniak.geek.market.service.ProductService;
import com.cherniak.geek.market.ws.GetProductsResponse;
import com.cherniak.geek.market.ws.ProductAdapter;
import com.cherniak.geek.market.ws.ProductWs;
import java.util.List;
import java.util.stream.Collectors;
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
    List<ProductWs> products = productService.findAll().stream()
        .map(ProductAdapter::requestProduct)
        .collect(Collectors.toList());

    productsResponse.setProducts(products);
    return productsResponse;
  }

}
