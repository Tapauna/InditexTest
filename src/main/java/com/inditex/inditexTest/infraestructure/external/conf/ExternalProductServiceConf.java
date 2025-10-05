package com.inditex.inditexTest.infraestructure.external.conf;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Bean that contains the parameters of the external service.
 */
@Configuration
@ConfigurationProperties(prefix="spring.external")
@Data
public class ExternalProductServiceConf {
      String baseUrl;
      String similarProductsUrl;
      String productDetailsUrl;
}
