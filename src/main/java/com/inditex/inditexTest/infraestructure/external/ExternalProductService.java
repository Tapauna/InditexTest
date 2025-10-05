package com.inditex.inditexTest.infraestructure.external;

import com.inditex.inditexTest.infraestructure.external.entity.ExternalProductDetails;
import java.util.List;

/**
 * Interface that represents the operations available in the external service.
 */
public interface ExternalProductService {

  /**
   * Given a product ID, the list of similar product IDs is retrieved.
   * @param productId Product ID to query.
   * @return the list of similar product ids
   */
  List<String> requestSimilarProducts(String productId);

  /**
   * Given a product ID, its details are retrieved.
   * @param productId Product ID to query.
   * @return the product details
   */
  ExternalProductDetails requestProductDetails(String productId);
}
