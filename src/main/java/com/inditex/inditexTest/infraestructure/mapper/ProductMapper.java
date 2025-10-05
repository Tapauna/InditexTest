package com.inditex.inditexTest.infraestructure.mapper;
import com.inditex.inditexTest.domain.Product;
import com.inditex.inditexTest.infraestructure.external.entity.ExternalProductDetails;
import com.inditex.inditexTest.infraestructure.redis.entity.ProductDetailsCacheEntity;
import java.util.Set;
import org.mapstruct.Mapper;
import org.openapitools.model.ProductDetail;

/**
 * Interface responsible for mapping to the different entities used in the service.
 */
@Mapper(componentModel = "spring")
public interface ProductMapper {
  ProductDetail productToProductDetail(Product src);

  Product externalProductDetailsToProduct(ExternalProductDetails src);

  Set<ProductDetail> productListToProductDetailList(Set<Product> src);

  Product productDetailsCcheToProduct(ProductDetailsCacheEntity src);

  ProductDetailsCacheEntity ExternalProductDetailstoProductDetailsCacheEntity(ExternalProductDetails src);
}
