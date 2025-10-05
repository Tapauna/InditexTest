package com.inditex.inditexTest.domain;

import java.util.List;

/**
 * Output port to the methods responsible for data retrieval.
 */
public interface ProductRepository {

  /**
   * Given a product ID, its details are retrieved.
   * @param productId Product ID to query.
   * @return the product details
   */
  Product getProductDetails(String productId);

  /**
   * Given a product ID, the list of similar product IDs is retrieved.
   * @param productId Product ID to query.
   * @return the list of similar product ids
   */
  List<String> getSimilarProductIds(String productId);
}
