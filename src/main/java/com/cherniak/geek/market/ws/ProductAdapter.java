package com.cherniak.geek.market.ws;

import com.cherniak.geek.market.model.Product;

public class ProductAdapter {

  /**
   * Преобразует сущность Product в транспортный объект Product.
   */
  public static ProductWs requestProduct(Product product) {
    ProductWs wsProduct = new ProductWs();
    wsProduct.setProductId(product.getId());
    wsProduct.setProductTitle(product.getTitle());
    wsProduct.setProductCost(product.getCost());
    wsProduct.setCategoryId(product.getCategory().getId());
    wsProduct.setCategoryTitle(product.getCategory().getTitle());
    return wsProduct;
  }


}
