package com.mca.demo.service;

import java.util.List;

import com.mca.demo.dto.ProductDetail;
import com.mca.demo.exception.CustomException;

public interface SimilarProductsService {

	public List<ProductDetail> getSimilarProducts(String productId) throws CustomException;
	
}
