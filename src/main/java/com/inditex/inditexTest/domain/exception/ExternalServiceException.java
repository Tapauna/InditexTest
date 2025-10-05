package com.inditex.inditexTest.domain.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

/**
 * Exception to indicate that the external service has failed.
 */
@Getter
@Setter
public class ExternalServiceException extends RuntimeException {

  private final HttpStatus HTTP_STATUS=HttpStatus.SERVICE_UNAVAILABLE;
  public ExternalServiceException(String message) {
    super(message);
  }
}
