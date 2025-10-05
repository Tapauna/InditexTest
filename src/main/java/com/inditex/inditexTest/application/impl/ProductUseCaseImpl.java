package com.inditex.inditexTest.application.impl;

import com.inditex.inditexTest.application.ProductUseCase;
import com.inditex.inditexTest.domain.Product;
import com.inditex.inditexTest.domain.ProductRepository;
import com.inditex.inditexTest.domain.exception.ProductBadRequestException;
import com.inditex.inditexTest.domain.exception.ProductNotFoundException;
import com.inditex.inditexTest.infraestructure.mapper.ProductMapper;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.model.ProductDetail;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * Class responsible for managing the business logic related to products.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductUseCaseImpl implements ProductUseCase {
  private static final String PRODUCT_ID_MISSING_MESSAGE="the product id is missing";
  private static final String NO_SIMILAR_PRODUCTS_MESSAGE="No similar product for this id";;
  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  /**
   * Method that handles the retrieval of similar products, given a product ID.
   * @param productId ID of the product we want to retrieve.
   * @return Set containing the similar products.
   */
  @Override
  public Set<ProductDetail> getSimilarProducts(String productId) {
    if(!StringUtils.hasText(productId)){
      throw new ProductBadRequestException(PRODUCT_ID_MISSING_MESSAGE);
    }
    List<String>similarProductIds=this.productRepository.getSimilarProductIds(productId);
    if(CollectionUtils.isEmpty(similarProductIds)){
      throw new ProductNotFoundException(NO_SIMILAR_PRODUCTS_MESSAGE);
    }
    log.info("Found {} products for {}",similarProductIds.size(),productId);
    Set<Product> similarProducts=new HashSet<>();
    similarProductIds.forEach(id ->{
      Product productRecovered=this.productRepository.getProductDetails(id);
      if(productRecovered!=null){
        log.info("Similar Product recovered for id {}: {}",id,productRecovered.toString());
        similarProducts.add(productRecovered);
      }
    });
    return productMapper.productListToProductDetailList(similarProducts);
  }
}
