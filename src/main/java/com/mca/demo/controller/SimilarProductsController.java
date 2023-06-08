package com.mca.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mca.demo.common.Constants;
import com.mca.demo.common.Utils;
import com.mca.demo.exception.CustomException;
import com.mca.demo.service.SimilarProductsService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/product")
public class SimilarProductsController {
	
	private static Logger logger = LoggerFactory.getLogger(SimilarProductsController.class);
	
	@Autowired
	private SimilarProductsService similarProductsService;
	
	@GetMapping("/{productId}/similar")
	public ResponseEntity<?> getSimilarProducts(@PathVariable String productId) {
		String prefLog = Utils.prefLog();
		logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_INICIO);
		
		try {
			var similarProducts = similarProductsService.getSimilarProducts(productId);
			logger.info(Constants.LOG_FORMAT_OK, prefLog, Utils.toJson(similarProducts));
			logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_FIN);
			return ResponseEntity.ok(similarProducts);
		} catch (CustomException e) {
			logger.error(Constants.LOG_FORMAT_ERROR, prefLog, e);
			logger.info(Constants.LOG_TWO_VALUE, prefLog, Constants.LOG_FIN);
			return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
		}
		
	}

}
