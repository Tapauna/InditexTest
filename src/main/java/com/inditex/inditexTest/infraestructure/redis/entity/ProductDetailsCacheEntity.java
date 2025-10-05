package com.inditex.inditexTest.infraestructure.redis.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Bean that represents the cached information of a product.
 */
@RedisHash("Product")
@Data
@Builder
public class ProductDetailsCacheEntity implements Serializable {

  @Id
  private String id;

  private String name;

  private BigDecimal price;

  private Boolean availability;
}
