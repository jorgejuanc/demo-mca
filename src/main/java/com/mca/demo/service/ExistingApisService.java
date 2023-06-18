package com.mca.demo.service;

import java.io.IOException;
import java.util.List;

import com.mca.demo.dto.ProductDetail;

import reactor.core.publisher.Mono;

public interface ExistingApisService {

	public Mono<List<String>> getSimilarIds(String productId) throws IOException;
    public Mono<ProductDetail> getProductDetailFromId(String productId) throws IOException;
    
}
