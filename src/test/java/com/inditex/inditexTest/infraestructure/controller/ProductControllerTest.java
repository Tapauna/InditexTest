package com.inditex.inditexTest.infraestructure.controller;

import static org.mockito.Mockito.when;

import com.inditex.inditexTest.application.ProductUseCase;
import com.inditex.inditexTest.domain.exception.ProductNotFoundException;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openapitools.model.ProductDetail;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
  @Mock
  private ProductUseCase productUseCase;

  @InjectMocks
  private ProductController productController;

  private Set<ProductDetail> productDetails;

  @Test
  void requestOk(){
      ProductDetail productDetail=new ProductDetail();
      productDetails= Set.of(productDetail);
    when(this.productUseCase.getSimilarProducts("1")).thenReturn(productDetails);
    ResponseEntity<Set<ProductDetail>>response=this.productController.getProductSimilar("1");
    assertEquals(200,response.getStatusCode().value());
    assertEquals(productDetails,response.getBody());
  }

  @Test
  void requestNotFound(){
    ProductDetail productDetail=new ProductDetail();
    productDetails= Set.of(productDetail);
    when(this.productUseCase.getSimilarProducts("3")).thenThrow(ProductNotFoundException.class);
    ProductNotFoundException exception=assertThrows(ProductNotFoundException.class, () -> {
        ResponseEntity<Set<ProductDetail>>response=this.productController.getProductSimilar("3");
        assertEquals(404,response.getStatusCode().value());
    });
  }

  @Test
  void badRequest(){
    ProductDetail productDetail=new ProductDetail();
    productDetails= Set.of(productDetail);
    when(this.productUseCase.getSimilarProducts("3")).thenThrow(ProductNotFoundException.class);
    ProductNotFoundException exception=assertThrows(ProductNotFoundException.class, () -> {
      ResponseEntity<Set<ProductDetail>>response=this.productController.getProductSimilar("3");
      assertEquals(400,response.getStatusCode().value());
    });
  }

}
