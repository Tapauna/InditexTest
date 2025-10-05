package com.inditex.inditexTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;
import org.openapitools.model.ProductDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClientException;
import org.springframework.web.util.UriComponentsBuilder;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
class InditexTestApplicationTests {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	void getSimilarProductsOk(){
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/product/1/similar");
		ResponseEntity<ProductDetail[]> response=restTemplate.getForEntity(uriBuilder.toUriString(),
				ProductDetail[].class);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}


	@Test
	void getSimilarProductsNotFound(){
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/product/7/similar");
		RestClientException exception= assertThrows(RestClientException.class, () ->{
			ResponseEntity<ProductDetail[]> response=restTemplate.getForEntity(uriBuilder.toUriString(),
					ProductDetail[].class);
			assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
		});
	}

	@Test
	void getSimilarProductsBadRequest(){
		UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/product//similar");
		RestClientException exception= assertThrows(RestClientException.class, () ->{
			ResponseEntity<ProductDetail[]> response=restTemplate.getForEntity(uriBuilder.toUriString(),
					ProductDetail[].class);
			assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		});
	}
}
