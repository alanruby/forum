package com.alan.forum.exception;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {
	
	@ExceptionHandler(value = ApiRequestException.class)
	public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
		
		ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.BAD_REQUEST, ZonedDateTime.now());
		
		
		return new ResponseEntity<>(apiException,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = NotFoundException.class)
	public ResponseEntity<Object> handleApiRequestException(NotFoundException e) {
		
		ApiException apiException = new ApiException(e.getMessage(), e, HttpStatus.NOT_FOUND, ZonedDateTime.now());
		
		
		return new ResponseEntity<>(apiException,HttpStatus.NOT_FOUND);
	}

}
