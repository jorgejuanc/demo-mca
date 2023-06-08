package com.mca.demo.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mca.demo.common.Constants;
import com.mca.demo.common.Utils;
import com.mca.demo.dto.ProductDetail;
import com.mca.demo.exception.CustomException;
import com.mca.demo.service.ExistingApisService;
import com.mca.demo.service.SimilarProductsService;

@Service
public class SimilarProductsServiceImpl implements SimilarProductsService {
	
	private static Logger logger = LoggerFactory.getLogger(SimilarProductsServiceImpl.class);
	
	@Autowired
	private ExistingApisService existingApisService;
	
	/**
	 * Obtiene una lista de detalles de productos similares a partir del ID de un producto dado
	 *
	 * @param productId el ID del producto para el cual se desean obtener productos similares
	 * @return una lista de objetos ProductDetail que representan los productos similares
	 * @throws CustomException si ocurre algún error durante la obtención de los productos similares
	 */
	public List<ProductDetail> getSimilarProducts(String productId) throws CustomException {
		String prefLog = Utils.prefLog();
		logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_INICIO);
		
		List<String> ids = existingApisService.getSimilarIds(productId);
		logger.info(Constants.LOG_FORMAT_OK, prefLog, ids);
		
		var response = ids.stream()
                .map(t -> {
					try {
						return existingApisService.getProductDetailFromId(t);
					} catch (CustomException e) {
						return null;
					}
				})
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
		
		logger.info(Constants.LOG_FORMAT_OK, prefLog, Utils.toJson(response));
		logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_FIN);
		return response;
	}

}
