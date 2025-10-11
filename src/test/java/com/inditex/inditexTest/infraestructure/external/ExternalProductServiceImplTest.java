package com.inditex.inditexTest.infraestructure.external;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import com.inditex.inditexTest.domain.exception.ExternalServiceException;
import com.inditex.inditexTest.infraestructure.external.conf.ExternalProductServiceConf;
import com.inditex.inditexTest.infraestructure.external.entity.ExternalProductDetails;
import com.inditex.inditexTest.infraestructure.external.impl.ExternalProductServiceImpl;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
public class ExternalProductServiceImplTest {
  @Mock
  private ExternalProductServiceConf externalProductServiceConf;
  @Mock
  RestClient.Builder builder;
  @Mock
  RestClient restClient;
  @Mock(answer = Answers.RETURNS_SELF)
  RestClient.RequestHeadersUriSpec<?> uriSpec;
  @Mock
  RestClient.RequestHeadersSpec<?> headersSpec;
  @Mock
  RestClient.ResponseSpec responseSpec;;

  private ExternalProductServiceImpl externalProductService;

  @BeforeEach
  void setUp() {
    when(externalProductServiceConf.getBaseUrl()).thenReturn("http://localhost:3001");
    when(builder.baseUrl(anyString())).thenReturn(builder);
    when(builder.build()).thenReturn(restClient);
    externalProductService=new ExternalProductServiceImpl(builder,externalProductServiceConf);
  }

  @Test
  void requestSimilarProductsTestOk(){
    when(externalProductServiceConf.getSimilarProductsUrl()).thenReturn("/product/{productId}/similarids");
    doReturn(uriSpec).when(restClient).get();
    doReturn(headersSpec).when(uriSpec).uri("/product/{productId}/similarids", "1");
    doReturn(responseSpec).when(headersSpec).retrieve();
    doReturn(new String[]{"2","3","4"}).when(responseSpec).body(String[].class);

    List<String> result = externalProductService.requestSimilarProducts("1");

    assertThat(result).containsExactly("2","3","4");
  }

  @Test
  void requestSimilarProductsTestNotFound(){
    HttpClientErrorException notFound =
        HttpClientErrorException.create(
            HttpStatus.NOT_FOUND,
            "Not Found",
            HttpHeaders.EMPTY,
            null,
            StandardCharsets.UTF_8
        );
    when(externalProductServiceConf.getSimilarProductsUrl()).thenReturn("/product/{productId}/similarids");
    doReturn(uriSpec).when(restClient).get();
    doReturn(headersSpec).when(uriSpec).uri("/product/{productId}/similarids", "10");
    doReturn(responseSpec).when(headersSpec).retrieve();
    doThrow(notFound).when(responseSpec).body(String[].class);

    List<String> result = externalProductService.requestSimilarProducts("10");

    Assertions.assertNull(result);
  }

  @Test
  void requestSimilarProductsTestServiceError(){
    when(externalProductServiceConf.getSimilarProductsUrl()).thenReturn("/product/{productId}/similarids");
    doReturn(uriSpec).when(restClient).get();
    doReturn(headersSpec).when(uriSpec).uri("/product/{productId}/similarids", "10");
    when(headersSpec.retrieve()).thenThrow(new RuntimeException("fail"));
    ExternalServiceException exception=assertThrows(ExternalServiceException.class, () ->{
      List<String> result = externalProductService.requestSimilarProducts("10");
    });
    Assertions.assertEquals(503,exception.getHTTP_STATUS().value());
  }

  @Test
  void requestProductDetailsTestOk(){
    ExternalProductDetails details=ExternalProductDetails.builder()
        .id("1")
        .name("Shirt")
        .price(BigDecimal.valueOf(9.99))
        .availability(true)
        .build();
    when(externalProductServiceConf.getProductDetailsUrl()).thenReturn("/product/{productId}");
    doReturn(uriSpec).when(restClient).get();
    doReturn(headersSpec).when(uriSpec).uri("/product/{productId}", "1");
    doReturn(responseSpec).when(headersSpec).retrieve();
    doReturn(details).when(responseSpec).body(ExternalProductDetails.class);

    ExternalProductDetails result = externalProductService.requestProductDetails("1");

    Assertions.assertEquals(details,result);
  }

  @Test
  void requestProductDetailsTestNotFound(){
    HttpClientErrorException notFound =
        HttpClientErrorException.create(
            HttpStatus.NOT_FOUND,
            "Not Found",
            HttpHeaders.EMPTY,
            null,
            StandardCharsets.UTF_8
        );
    when(externalProductServiceConf.getProductDetailsUrl()).thenReturn("/product/{productId}");
    doReturn(uriSpec).when(restClient).get();
    doReturn(headersSpec).when(uriSpec).uri("/product/{productId}", "10");
    doReturn(responseSpec).when(headersSpec).retrieve();
    doThrow(notFound).when(responseSpec).body(ExternalProductDetails.class);

    ExternalProductDetails result = externalProductService.requestProductDetails("10");

    Assertions.assertNull(result);
  }

  @Test
  void requestProductDetailsTestServiceError(){
    when(externalProductServiceConf.getSimilarProductsUrl()).thenReturn("/product/{productId}");
    doReturn(uriSpec).when(restClient).get();
    doReturn(headersSpec).when(uriSpec).uri("/product/{productId}", "10");
    when(headersSpec.retrieve()).thenThrow(new RuntimeException("fail"));
    ExternalServiceException exception=assertThrows(ExternalServiceException.class, () ->{
      List<String> result = externalProductService.requestSimilarProducts("10");
    });
    Assertions.assertEquals(503,exception.getHTTP_STATUS().value());
  }
}
