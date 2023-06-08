package com.mca.demo.service.impl;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mca.demo.common.Constants;
import com.mca.demo.common.Utils;
import com.mca.demo.dto.ProductDetail;
import com.mca.demo.exception.CustomException;
import com.mca.demo.service.ExistingApisService;
import com.mca.demo.service.RestApiService;

@Service
public class ExistingApisServiceImpl implements ExistingApisService {
	
	private static Logger logger = LoggerFactory.getLogger(ExistingApisServiceImpl.class);
	
	@Value("${apis.host}" + "${apis.similarIds}")
	private String urlSimilarIds;
	
	@Value("${apis.host}" + "${apis.productDetail}")
	private String urlProductDetail;
	
	@Autowired
	private RestApiService restApiService;
	
	@Autowired
	private Gson gson;
	
	
	/**
	 * Obtiene una lista de IDs similares para un producto dado.
     *
     * @param productId el ID del producto para el cual se desea obtener IDs similares
     * 
     * @return una lista de IDs similares en formato de cadena de texto
     * @throws CustomException si ocurre algún error durante la obtención de los IDs similares
     */
	@Override
	public List<String> getSimilarIds(String productId) throws CustomException {
		String prefLog = Utils.prefLog();
		logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_INICIO);
		
		URI url = UriComponentsBuilder.fromHttpUrl(
				urlSimilarIds.replace(Constants.PARAM_PRODUCT_ID, productId)).build().toUri();
		String plainResponse = null;
		try {
			plainResponse = restApiService.callRestApi(url, HttpMethod.GET, null);
			logger.info(Constants.LOG_FORMAT_OK, prefLog, plainResponse);
		} catch (IOException e) {
			logger.error(Constants.LOG_FORMAT_ERROR, prefLog, e);
			logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_FIN);
			throw new CustomException(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		Type type = new TypeToken<List<String>>() {}.getType();
		List<String> response = gson.fromJson(plainResponse, type);
		
		logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_FIN);
		return response;
	}

	/**
     * Obtiene los detalles de un producto a partir de su ID.
     *
     * @param productId el ID del producto del que se desean obtener los detalles
     * 
     * @return objeto ProductDetail que contiene los detalles del producto
     * @throws CustomException si ocurre algún error durante la obtención de los detalles del producto
     */
	@Override
	public ProductDetail getProductDetailFromId(String productId) throws CustomException {
		String prefLog = Utils.prefLog();
		logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_INICIO);
		
		URI url = UriComponentsBuilder.fromHttpUrl(
				urlProductDetail.replace(Constants.PARAM_PRODUCT_ID, productId)).build().toUri();
		String plainResponse = null;
		try {
			plainResponse = restApiService.callRestApi(url, HttpMethod.GET, null);
			logger.info(Constants.LOG_FORMAT_OK, prefLog, plainResponse);
		} catch (IOException e) {
			logger.error(Constants.LOG_FORMAT_ERROR, prefLog, e);
			logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_FIN);
			throw new CustomException(e.getMessage(), HttpStatus.NOT_FOUND);
		}
		
		Type type = new TypeToken<ProductDetail>() {}.getType();
		ProductDetail response = gson.fromJson(plainResponse, type);
		
		logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_FIN);
		return response;
	}

}
