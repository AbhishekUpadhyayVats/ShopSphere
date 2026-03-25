package com.example.catalog.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleGlobalException {
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleProductNotFoundExcepiton(ProductNotFoundException ex){
		Map<String,String> map = new HashMap<>();
		
		map.put("Error:", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleCategoryNotFoundException(CategoryNotFoundException ex){
		Map<String,String> map = new HashMap<>();
		
		map.put("Error:", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(map);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handlMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

	    Map<String, String> map = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(error -> {
	        map.put(error.getField(), error.getDefaultMessage());
	    });

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
	

	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,String>> handleAllException(Exception ex){
		Map<String,String> map = new HashMap<>();
		map.put("Error:", ex.getMessage());
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
}
