package com.inditex.inditexTest.infraestructure.controller;

import com.inditex.inditexTest.application.ProductUseCase;
import java.math.BigDecimal;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.ProductApi;
import org.openapitools.model.ProductDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller where the inputs to the Products service are managed.
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController implements ProductApi {
  private final ProductUseCase productUseCase;

  /**
   * Given a product ID, this endpoint retrieves the similar products.
   * @param productId  (required) The product ID from which we want to retrieve its similar products.
   * @return A set with the similar products.
   */
  @Override
  public ResponseEntity<Set<ProductDetail>> getProductSimilar(String productId) {
    log.info(String.format("ENTER WITH PRODUCT  ID %s",productId));
    return ResponseEntity.ok(productUseCase.getSimilarProducts(productId));
  }
}
