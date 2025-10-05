package com.inditex.inditexTest.application;

import java.util.List;
import java.util.Set;
import org.openapitools.model.ProductDetail;

public interface ProductUseCase {

    /**
     * Given a product ID, this method retrieves the similar products.
     * @param productId The product ID from which we want to retrieve its similar products.
     * @return A set with the similar products.
     */
    Set<ProductDetail> getSimilarProducts(String productId);
}
