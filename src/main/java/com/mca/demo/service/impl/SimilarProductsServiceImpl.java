package com.mca.demo.service.impl;

import java.io.IOException;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mca.demo.common.Utils;
import com.mca.demo.dto.ProductDetail;
import com.mca.demo.service.ExistingApisService;
import com.mca.demo.service.SimilarProductsService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class SimilarProductsServiceImpl implements SimilarProductsService {
	
	@Autowired
	private ExistingApisService existingApisService;
	
	public Flux<ProductDetail> getSimilarProducts(String productId) throws IOException {
	    log.info("Inicio de getSimilarProducts");
	    
	    return existingApisService.getSimilarIds(productId)
	            .doOnNext(ids -> log.info("Respuesta: " + ids))
	            .flatMapMany(Flux::fromIterable)
	            .flatMap(id -> {
					try {
						return existingApisService.getProductDetailFromId(id)
						                .onErrorResume(e -> {
						                    log.error("Error en getSimilarProducts");
						                    return Mono.empty();
						                });
					} catch (IOException e) {
						return null;
					}
				})
	            .filter(Objects::nonNull)
	            .collectList()
	            .doOnNext(response -> {
	                log.info("Respuesta: " + Utils.toJson(response));
	                log.info("Fin de getSimilarProducts");
	            })
	            .flatMapMany(Flux::fromIterable);
	}

}
