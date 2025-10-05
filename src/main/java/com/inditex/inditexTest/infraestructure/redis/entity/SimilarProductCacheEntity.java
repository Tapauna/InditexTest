package com.inditex.inditexTest.infraestructure.redis.entity;

import java.util.List;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

/**
 * Bean that represents the cached response of the call to the external service in similarIds.
 */
@RedisHash("Similar")
@Data
@Builder
public class SimilarProductCacheEntity {
  @Id
  private String id;
  private List<String> similarIds;
}
