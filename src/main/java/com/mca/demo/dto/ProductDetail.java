package com.mca.demo.dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Builder
@Getter
@Setter
public class ProductDetail {
	
	@NotNull
	@Size(min=1)
	@JsonProperty("id")
	private String id;
	
	@NotNull
	@Size(min=1)
	@JsonProperty("name")
    private String name;
    
	@NotNull
	@JsonProperty("price")
    private BigDecimal price;
    
	@NotNull
	@JsonProperty("availability")
    private Boolean availability;
}
