package com.inditex.inditexTest.application;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.inditex.inditexTest.application.impl.ProductUseCaseImpl;
import com.inditex.inditexTest.domain.Product;
import com.inditex.inditexTest.domain.ProductRepository;
import com.inditex.inditexTest.domain.exception.ProductBadRequestException;
import com.inditex.inditexTest.domain.exception.ProductNotFoundException;
import com.inditex.inditexTest.infraestructure.mapper.ProductMapper;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.ProductDetail;

@ExtendWith(MockitoExtension.class)
public class ProductUseCaseImplTest {

  @Mock
  private ProductRepository productRepository;

  @Mock
  private ProductMapper productMapper;

  @InjectMocks
  private ProductUseCaseImpl productUseCase;

  private Set<Product> productList;

  private Product product;

  private Set<ProductDetail> productDetailList;

  @BeforeEach
  void setUp(){
    this.product =Product.builder()
        .id("2")
        .price(BigDecimal.TEN)
        .name("PRODUCT 2")
        .availability(true)
        .build();
    ProductDetail productDetail=new ProductDetail();
    productDetail.setId("2");
    productDetail.setPrice(BigDecimal.TEN);
    productDetail.setName("PRODUCT 2");
    productDetail.setAvailability(true);
    this.productList=Set.of(this.product);
    this.productDetailList =Set.of(productDetail);
  }

  @Test
  void recoverSimilarProductsOk(){
    when(this.productRepository.getSimilarProductIds("1")).thenReturn(List.of("2"));
    when(this.productRepository.getProductDetails("2")).thenReturn(product);
    when(this.productMapper.productListToProductDetailList(this.productList)).thenReturn(this.productDetailList);
    Set<ProductDetail> result=this.productUseCase.getSimilarProducts("1");
    assertEquals(this.productDetailList,result);
  }

  @Test
  void recoverSimilarProductsProductIdMissing(){
    ProductBadRequestException exception= assertThrows(ProductBadRequestException.class, () ->{
      Set<ProductDetail> result=this.productUseCase.getSimilarProducts(null);
    });
    assertEquals(400,exception.getHTTP_STATUS().value());
    ProductBadRequestException exception2= assertThrows(ProductBadRequestException.class, () ->{
      Set<ProductDetail> result=this.productUseCase.getSimilarProducts("");
    });
    assertEquals(400,exception2.getHTTP_STATUS().value());
  }

  @Test
  void recoverSimilarProductsNotFound(){
    when(this.productRepository.getSimilarProductIds("3")).thenReturn(List.of());
    ProductNotFoundException exception=assertThrows(ProductNotFoundException.class, () ->{
      Set<ProductDetail> result=this.productUseCase.getSimilarProducts("3");
    });
    assertEquals(404,exception.getHTTP_STATUS().value());
    when(this.productRepository.getSimilarProductIds("3")).thenReturn(null);
    ProductNotFoundException exception2=assertThrows(ProductNotFoundException.class, () ->{
      Set<ProductDetail> result=this.productUseCase.getSimilarProducts("3");
    });
    assertEquals(404,exception2.getHTTP_STATUS().value());
  }

  @Test
  void recoverSimilarProductsSomeNull(){
    when(this.productRepository.getSimilarProductIds("1")).thenReturn(List.of("2","3"));
    when(this.productRepository.getProductDetails("2")).thenReturn(product);
    when(this.productRepository.getProductDetails("3")).thenReturn(null);
    when(this.productMapper.productListToProductDetailList(this.productList)).thenReturn(this.productDetailList);
    Set<ProductDetail> result=this.productUseCase.getSimilarProducts("1");
    assertEquals(1,result.size());
  }
}
