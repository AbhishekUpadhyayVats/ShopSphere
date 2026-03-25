package com.lpu.order_service.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandleGlobalException {
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleOrderNotFoundException(OrderNotFoundException ex){
		
		Map<String,String> map = new HashMap<>();
		
		map.put("Error:", ex.getMessage());
		
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderItemNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleOrderItemNotFoundException(OrderItemNotFoundException ex){
		
		Map<String,String> map = new HashMap<>();
		
		map.put("Error:", ex.getMessage());
		
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CartNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleCartNotFoundException(CartNotFoundException ex){
		
		Map<String,String> map = new HashMap<>();
		
		map.put("Error:", ex.getMessage());
		
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(CartItemNotFoundException.class)
	public ResponseEntity<Map<String,String>> handleCartItemNotFoundException(CartItemNotFoundException ex){
		
		Map<String,String> map = new HashMap<>();
		
		map.put("Error:", ex.getMessage());
		
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<Map<String,String>> handleAllException(Exception ex){
		
		Map<String,String> map = new HashMap<>();
		
		map.put("Error:", ex.getMessage());
		
		return new ResponseEntity<Map<String,String>>(map,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		Map<String,String> map = new HashMap<>();
		
		ex.getBindingResult().getFieldErrors().forEach(err -> map.put(err.getField(), err.getDefaultMessage()));
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(map);
	}
}
