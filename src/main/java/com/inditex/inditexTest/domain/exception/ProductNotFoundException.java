package com.inditex.inditexTest.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Exception to indicate that the service has not retrieved similar products.
 */
@Getter
@Setter
public class ProductNotFoundException extends RuntimeException {

  private final HttpStatus HTTP_STATUS=HttpStatus.NOT_FOUND;

  public ProductNotFoundException(String message) {
    super(message);
  }
}
