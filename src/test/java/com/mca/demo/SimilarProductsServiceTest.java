package com.mca.demo;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.mca.demo.dto.ProductDetail;
import com.mca.demo.exception.CustomException;
import com.mca.demo.service.impl.ExistingApisServiceImpl;
import com.mca.demo.service.impl.SimilarProductsServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class SimilarProductsServiceTest {
	
	@InjectMocks
	private SimilarProductsServiceImpl similarProductsService;
	
	@Mock
	private ExistingApisServiceImpl existingApisService;

	@Test
	@DisplayName("Comprobar que el servicio funciona bien si las APIs funcionan bien")
	public void whenBothApisAreOK_thenReturnProductDetails() throws CustomException {
		var mockResponseSimilarIds = Arrays.asList("2", "2", "2");
		var mockProductDetail = ProductDetail.builder()
				.id("2")
				.name("mockName1")
				.price(new BigDecimal(12.99))
				.availability(true)
				.build();
		var expectedResponse = Arrays.asList(mockProductDetail, mockProductDetail, mockProductDetail);
		
		when(existingApisService.getSimilarIds(any())).thenReturn(mockResponseSimilarIds);
		when(existingApisService.getProductDetailFromId(any())).thenReturn(mockProductDetail);
		
		var response = similarProductsService.getSimilarProducts("1");
		
		assertIterableEquals(expectedResponse, response);
		verify(existingApisService, times(3)).getProductDetailFromId(any());
		
	}
	
	@Test
	@DisplayName("Comprobar que el servicio lanza exception cuando getSimilarIds lanza exception")
	public void whenSimilarIdsThrowsException_thenThrowException() throws CustomException {
		when(existingApisService.getSimilarIds(any())).thenThrow(new CustomException("", HttpStatus.NOT_FOUND));
		
		assertThrows(CustomException.class, () -> similarProductsService.getSimilarProducts("1"));
        verify(existingApisService, times(1)).getSimilarIds("1");
		
	}
	
	@Test
	@DisplayName("Comprobar lista vacia cuando getProductDetailFromId lanza exception")
	public void whenProductDetailFromIdThrowsException_thenReturnEmptyList() throws CustomException {
		var mockResponseSimilarIds = Arrays.asList("2", "2", "2");
		var expectedResponse = Arrays.asList();
		
		when(existingApisService.getSimilarIds(any())).thenReturn(mockResponseSimilarIds);
		when(existingApisService.getProductDetailFromId(any())).thenThrow(new CustomException("", HttpStatus.NOT_FOUND));
		
		var response = similarProductsService.getSimilarProducts("1");
		
		assertIterableEquals(expectedResponse, response);
		verify(existingApisService, times(3)).getProductDetailFromId(any());
		
	}

}
