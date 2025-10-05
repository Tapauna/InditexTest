package com.inditex.inditexTest.infraestructure.external.impl;

import com.inditex.inditexTest.domain.exception.ExternalServiceException;
import com.inditex.inditexTest.domain.exception.ProductNotFoundException;
import com.inditex.inditexTest.infraestructure.external.ExternalProductService;
import com.inditex.inditexTest.infraestructure.external.conf.ExternalProductServiceConf;
import com.inditex.inditexTest.infraestructure.external.entity.ExternalProductDetails;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException.NotFound;
import org.springframework.web.client.RestClient;

/**
 * Implementation of the calls to the external service.
 */
@Service
@Slf4j
public class ExternalProductServiceImpl implements ExternalProductService {

  private RestClient restClient;
  private final ExternalProductServiceConf externalProductServiceConf;


  public ExternalProductServiceImpl(RestClient.Builder restClientBuilder,
      ExternalProductServiceConf externalProductServiceConf) {
    this.externalProductServiceConf=externalProductServiceConf;
    this.restClient = restClientBuilder.baseUrl(this.externalProductServiceConf.getBaseUrl()).build();
  }

  /**
   * Given a product ID, the list of similar product IDs is retrieved.
   * @param productId Product ID to query.
   * @return the list of similar product ids
   */
  @Override
  public List<String> requestSimilarProducts(String productId) {
    List<String> response=new ArrayList<>();
    log.info("Calling similarProductsEndpoint {} for {}",externalProductServiceConf.getBaseUrl(),productId);
    try{
      String[] similarIds=this.restClient.get()
          .uri(externalProductServiceConf.getSimilarProductsUrl(),productId)
          .retrieve()
          .body(String[].class);
      if(similarIds!=null){
        response.addAll(List.of(similarIds));
      }
      return response;
    } catch (NotFound e){
      return null;
    }catch (Exception e){
      throw new ExternalServiceException("External service fails");
    }
  }

  /**
   * Given a product ID, its details are retrieved.
   * @param productId Product ID to query.
   * @return the product details
   */
  @Override
  public ExternalProductDetails requestProductDetails(String productId) {
    log.info("Calling ProductsDetailsEndpoint for {}",productId);
    try {
      return this.restClient.get()
          .uri(externalProductServiceConf.getProductDetailsUrl(),productId)
          .retrieve()
          .body(ExternalProductDetails.class);
    } catch (NotFound e){
      return null;
    }catch (Exception e){
      throw new ExternalServiceException("External service fails");
    }
  }
}
