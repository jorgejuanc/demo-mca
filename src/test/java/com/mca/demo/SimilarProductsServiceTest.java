package com.mca.demo;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mca.demo.dto.ProductDetail;
import com.mca.demo.exception.CustomException;
import com.mca.demo.service.impl.ExistingApisServiceImpl;
import com.mca.demo.service.impl.SimilarProductsServiceImpl;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SimilarProductsServiceTest {
	
	@InjectMocks
	private SimilarProductsServiceImpl similarProductsService;
	
	@Mock
	private ExistingApisServiceImpl existingApisService;

	@Test
	@DisplayName("Comprobar que el servicio funciona bien si las APIs funcionan bien")
	public void whenBothApisAreOK_thenReturnProductDetails() throws CustomException, IOException {
		var mockResponseSimilarIds = Arrays.asList("2", "2", "2");
		var mockProductDetail = ProductDetail.builder()
				.id("2")
				.name("mockName1")
				.price(new BigDecimal(12.99))
				.availability(true)
				.build();
		
		when(existingApisService.getSimilarIds(any())).thenReturn(Mono.just(mockResponseSimilarIds));
		when(existingApisService.getProductDetailFromId(any())).thenReturn(Mono.just(mockProductDetail));
		
		var response = similarProductsService.getSimilarProducts("1");
		
		StepVerifier
		.create(response)
		.expectNext(mockProductDetail, mockProductDetail, mockProductDetail)
		.verifyComplete();
		
		verify(existingApisService, times(3)).getProductDetailFromId(any());
		
	}
	
	@Test
	@DisplayName("Comprobar que el servicio lanza exception cuando getSimilarIds lanza exception")
	public void whenSimilarIdsThrowsException_thenThrowException() throws IOException {
		when(existingApisService.getSimilarIds(any())).thenThrow(new IOException(""));
		
		assertThrows(IOException.class, () -> similarProductsService.getSimilarProducts("1"));
        verify(existingApisService, times(1)).getSimilarIds("1");
		
	}

}
