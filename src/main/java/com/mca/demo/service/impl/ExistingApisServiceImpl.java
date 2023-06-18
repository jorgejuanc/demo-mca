package com.mca.demo.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mca.demo.common.Constants;
import com.mca.demo.dto.ProductDetail;
import com.mca.demo.exception.CustomException;
import com.mca.demo.service.ExistingApisService;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ExistingApisServiceImpl implements ExistingApisService {
	
	@Value("${apis.host}" + "${apis.similarIds}")
	private String urlSimilarIds;
	
	@Value("${apis.host}" + "${apis.productDetail}")
	private String urlProductDetail;
	
	@Autowired
    private WebClient webClient;
	
	@Autowired
	private Gson gson;
	
	
	@Override
	public Mono<List<String>> getSimilarIds(String productId) throws IOException {
	    return makeRequest(productId, urlSimilarIds, new TypeToken<List<String>>() {}.getType(), "getSimilarIds");
	}

	@Override
	public Mono<ProductDetail> getProductDetailFromId(String productId) throws IOException {
	    return makeRequest(productId, urlProductDetail, new TypeToken<ProductDetail>() {}.getType(), "getProductDetailFromId");
	}

	private <T> Mono<T> makeRequest(String productId, String urlTemplate, Type type, String logInfo) throws IOException {
	    log.info("Inicio de " + logInfo);

	    URI url = UriComponentsBuilder.fromHttpUrl(
	            urlTemplate.replace(Constants.PARAM_PRODUCT_ID, productId)).build().toUri();

	    RequestBodySpec requestSpec = webClient
	            .method(HttpMethod.GET)
	            .uri(url);

	    return requestSpec.retrieve()
	            .bodyToMono(String.class)
	            .onErrorMap(e -> new RuntimeException("Error en callRestApi: URL -> " + url, e))
	            .doOnSuccess(plainResponse -> log.info("Respuesta: " + plainResponse))
	            .doOnError(e -> {
	                log.error("Error en " + logInfo);
	                log.info("Fin de " + logInfo);
	            })
	            .onErrorResume(e -> Mono.error(new CustomException(e.getMessage(), HttpStatus.NOT_FOUND)))
	            .flatMap(plainResponse -> {
	                T response = gson.fromJson(plainResponse, type);
	                log.info("Fin de " + logInfo);
	                return Mono.just(response);
	            });
	}


}
