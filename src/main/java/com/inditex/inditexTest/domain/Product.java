package com.inditex.inditexTest.domain;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 * Bean that represents the Product model.
 */
@Data
@Builder
public class Product {
  private String id;

  private String name;

  private BigDecimal price;

  private Boolean availability;
}
