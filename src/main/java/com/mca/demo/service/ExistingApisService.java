package com.mca.demo.service;

import java.util.List;

import com.mca.demo.dto.ProductDetail;
import com.mca.demo.exception.CustomException;

public interface ExistingApisService {

	public List<String> getSimilarIds(String productId) throws CustomException;
    public ProductDetail getProductDetailFromId(String productId) throws CustomException;
    
}
