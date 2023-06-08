package com.mca.demo.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CustomException extends Exception {

	private static final long serialVersionUID = 1L;
	
	private HttpStatus httpStatus;
	
	public CustomException(String message, HttpStatus httpStatus) {
        super(message);
        this.setHttpStatus(httpStatus);
    }

}
