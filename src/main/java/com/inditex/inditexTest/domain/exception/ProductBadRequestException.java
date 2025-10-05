package com.inditex.inditexTest.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Exception to indicate that the service has received a bad request.
 */
@Getter
@Setter
public class ProductBadRequestException extends RuntimeException {

  private final HttpStatus HTTP_STATUS=HttpStatus.BAD_REQUEST;
  public ProductBadRequestException(String message) {
    super(message);
  }
}
