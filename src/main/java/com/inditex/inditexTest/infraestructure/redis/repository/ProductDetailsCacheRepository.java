package com.inditex.inditexTest.infraestructure.redis.repository;

import com.inditex.inditexTest.infraestructure.redis.entity.ProductDetailsCacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository that manages the cached information of previous calls to the external service.
 */
@Repository
public interface ProductDetailsCacheRepository extends CrudRepository<ProductDetailsCacheEntity, String> {

}
