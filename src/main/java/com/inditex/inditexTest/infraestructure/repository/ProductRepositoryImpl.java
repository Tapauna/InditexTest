package com.inditex.inditexTest.infraestructure.repository;

import com.inditex.inditexTest.domain.Product;
import com.inditex.inditexTest.domain.ProductRepository;
import com.inditex.inditexTest.infraestructure.external.ExternalProductService;
import com.inditex.inditexTest.infraestructure.external.entity.ExternalProductDetails;
import com.inditex.inditexTest.infraestructure.mapper.ProductMapper;
import com.inditex.inditexTest.infraestructure.redis.entity.ProductDetailsCacheEntity;
import com.inditex.inditexTest.infraestructure.redis.entity.SimilarProductCacheEntity;
import com.inditex.inditexTest.infraestructure.redis.repository.ProductDetailsCacheRepository;
import com.inditex.inditexTest.infraestructure.redis.repository.SimilarProductCacheRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 * Implementation of the output ports responsible for retrieving the information.
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class ProductRepositoryImpl implements ProductRepository {

  private final ExternalProductService externalProductService;
  private final ProductDetailsCacheRepository productDetailsCacheRepository;
  private final SimilarProductCacheRepository similarProductCacheRepository;
  private final ProductMapper productMapper;

  /**
   * Given a product ID, its details are retrieved.
   * @param productId Product ID to query.
   * @return the product details
   */
  @Override
  public Product getProductDetails(String productId) {
    Optional<ProductDetailsCacheEntity> productDetailsCache= this.productDetailsCacheRepository.findById(productId);
    if(productDetailsCache.isPresent()){
      log.info("PRODUCT DETAIL RECOVERED FROM REDIS {}",productDetailsCache.get());
      return this.productMapper.productDetailsCcheToProduct(productDetailsCache.get());
    }
    ExternalProductDetails externalProductDetails=this.externalProductService.requestProductDetails(productId);
    if(externalProductDetails!=null){
      this.productDetailsCacheRepository.save
          (this.productMapper.ExternalProductDetailstoProductDetailsCacheEntity(externalProductDetails));
      return this.productMapper.externalProductDetailsToProduct(externalProductDetails);
    }
    return null;
  }

  /**
   * Given a product ID, the list of similar product IDs is retrieved.
   * @param productId Product ID to query.
   * @return the list of similar product ids
   */
  @Override
  public List<String> getSimilarProductIds(String productId) {
    Optional<SimilarProductCacheEntity> similarIdsCache=this.similarProductCacheRepository.findById(productId);
    if(similarIdsCache.isPresent()){
      log.info("SIMILAR PRODUCTS RECOVERED FROM REDIS {}",similarIdsCache.get().getSimilarIds());
      return similarIdsCache.get().getSimilarIds();
    }
    return this.externalProductService.requestSimilarProducts(productId);
  }
}
