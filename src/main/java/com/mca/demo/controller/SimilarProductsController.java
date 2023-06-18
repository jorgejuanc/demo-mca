package com.mca.demo.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mca.demo.dto.ProductDetail;
import com.mca.demo.service.SimilarProductsService;

import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/product")
public class SimilarProductsController {
    
	@Autowired
    private SimilarProductsService similarProductsService;
    
	@GetMapping("/{productId}/similar")
    public Flux<ProductDetail> getSimilarProducts(@PathVariable String productId) throws IOException {
        return similarProductsService.getSimilarProducts(productId);
    }
}
