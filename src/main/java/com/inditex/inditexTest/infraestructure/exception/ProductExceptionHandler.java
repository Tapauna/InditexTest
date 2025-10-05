package com.inditex.inditexTest.infraestructure.exception;

import com.inditex.inditexTest.domain.exception.ExternalServiceException;
import com.inditex.inditexTest.domain.exception.ProductBadRequestException;
import com.inditex.inditexTest.domain.exception.ProductNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Handler in charge of managing all possible exceptions that may arise from operations.
 * Depending on the exception, it returns its corresponding error code and an informative message.
 */
@RestControllerAdvice
public class ProductExceptionHandler {

  @ExceptionHandler(value = ProductBadRequestException.class)
  public ResponseEntity<String> productBadRequestExceptionHandler(ProductBadRequestException ex){
    return new ResponseEntity<>(ex.getMessage(), ex.getHTTP_STATUS());
  }

  @ExceptionHandler(value = ProductNotFoundException.class)
  public ResponseEntity<String> productNotFoundExceptionHandler(ProductNotFoundException ex){
    return new ResponseEntity<>(ex.getMessage(), ex.getHTTP_STATUS());
  }

  @ExceptionHandler(value = ExternalServiceException.class)
  public ResponseEntity<String> externalServiceExceptionHandler(ExternalServiceException ex){
    return new ResponseEntity<>(ex.getMessage(), ex.getHTTP_STATUS());
  }

}
