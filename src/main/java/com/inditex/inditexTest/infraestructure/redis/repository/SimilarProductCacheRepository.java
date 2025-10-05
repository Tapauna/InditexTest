package com.inditex.inditexTest.infraestructure.redis.repository;

import com.inditex.inditexTest.infraestructure.redis.entity.SimilarProductCacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * /**
 *  * Repository that manages the cached information of previous calls to the external service.
 *  */
@Repository
public interface SimilarProductCacheRepository extends
    CrudRepository<SimilarProductCacheEntity, String> {

}
