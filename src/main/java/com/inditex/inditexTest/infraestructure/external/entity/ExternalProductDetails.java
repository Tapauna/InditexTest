package com.inditex.inditexTest.infraestructure.external.entity;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

/**
 * Bean that represents the information retrieved from the external service.
 */
@Data
@Builder
public class ExternalProductDetails {
  private String id;

  private String name;

  private BigDecimal price;

  private Boolean availability;
}
