package com.spring.demo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.dao.DuplicateKeyException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// DB Exceptions

	@ExceptionHandler(DuplicateKeyException.class)
	public ResponseEntity<?> duplicateKeyException(DuplicateKeyException ex) {
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);

	}
	
	
	
	

}
