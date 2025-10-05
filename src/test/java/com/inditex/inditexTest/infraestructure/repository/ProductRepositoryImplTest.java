package com.inditex.inditexTest.infraestructure.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.inditex.inditexTest.domain.Product;
import com.inditex.inditexTest.infraestructure.external.ExternalProductService;
import com.inditex.inditexTest.infraestructure.external.entity.ExternalProductDetails;
import com.inditex.inditexTest.infraestructure.mapper.ProductMapper;
import com.inditex.inditexTest.infraestructure.redis.entity.ProductDetailsCacheEntity;
import com.inditex.inditexTest.infraestructure.redis.entity.SimilarProductCacheEntity;
import com.inditex.inditexTest.infraestructure.redis.repository.ProductDetailsCacheRepository;
import com.inditex.inditexTest.infraestructure.redis.repository.SimilarProductCacheRepository;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryImplTest {

  @Mock
  private ExternalProductService externalProductService;

  @Mock
  private ProductDetailsCacheRepository productDetailsCacheRepository;

  @Mock
  private SimilarProductCacheRepository similarProductCacheRepository;

  @Mock
  private ProductMapper productMapper;

  @InjectMocks
  private ProductRepositoryImpl productRepository;


  @Test
  void getProductDetailsOk(){
    ExternalProductDetails externalProductDetails=ExternalProductDetails.builder()
        .id("1")
        .name("Product 1")
        .availability(true)
        .price(BigDecimal.TEN)
        .build();
    Product product=Product.builder()
        .id("1")
        .name("Product 1")
        .availability(true)
        .price(BigDecimal.TEN)
        .build();

    when(this.productDetailsCacheRepository.findById("1")).thenReturn(Optional.empty());
    when(this.externalProductService.requestProductDetails("1")).thenReturn(externalProductDetails);
    when(this.productMapper.externalProductDetailsToProduct(externalProductDetails)).thenReturn(product);
    Product result=this.productRepository.getProductDetails("1");
    assertEquals(product,result);
  }

  @Test
  void getProductDetailsCacheOk(){
    ProductDetailsCacheEntity productDetailsCacheEntity=ProductDetailsCacheEntity.builder()
        .id("1")
        .name("Product 1")
        .availability(true)
        .price(BigDecimal.TEN)
        .build();
    Product product=Product.builder()
        .id("1")
        .name("Product 1")
        .availability(true)
        .price(BigDecimal.TEN)
        .build();

    when(this.productDetailsCacheRepository.findById("1")).thenReturn(Optional.of(productDetailsCacheEntity));
    when(this.productMapper.productDetailsCcheToProduct(productDetailsCacheEntity)).thenReturn(product);
    Product result=this.productRepository.getProductDetails("1");
    assertEquals(product,result);
  }

  @Test
  void getProductDetailsNull(){
    when(this.productDetailsCacheRepository.findById("1")).thenReturn(Optional.empty());
    when(this.externalProductService.requestProductDetails("1")).thenReturn(null);
    Product result=this.productRepository.getProductDetails("1");
    assertNull(result);
  }




  @Test
  void getSimilarProductIdsOk(){
    List<String> similarIds=List.of("2","3");
    when(this.similarProductCacheRepository.findById("1")).thenReturn(Optional.empty());
    when(this.externalProductService.requestSimilarProducts("1")).thenReturn(similarIds);
    List<String> result=this.productRepository.getSimilarProductIds("1");
    assertEquals(similarIds,result);
  }

  @Test
  void getSimilarProductIdsCacheOk(){
    List<String> similarIds=List.of("2","3");
    SimilarProductCacheEntity similarProductCacheEntity=SimilarProductCacheEntity.builder()
        .id("1")
        .similarIds(similarIds)
        .build();
    when(this.similarProductCacheRepository.findById("1")).thenReturn(Optional.of(similarProductCacheEntity));
    List<String> result=this.productRepository.getSimilarProductIds("1");
    assertEquals(similarIds,result);
  }
}
