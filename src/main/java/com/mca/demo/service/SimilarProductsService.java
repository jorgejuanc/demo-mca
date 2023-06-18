package com.mca.demo.service;

import java.io.IOException;

import com.mca.demo.dto.ProductDetail;

import reactor.core.publisher.Flux;

public interface SimilarProductsService {

	public Flux<ProductDetail> getSimilarProducts(String productId) throws IOException;
	
}
