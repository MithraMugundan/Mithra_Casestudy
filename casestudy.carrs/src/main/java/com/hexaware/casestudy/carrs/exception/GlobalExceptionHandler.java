package com.hexaware.casestudy.carrs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;



@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler({UserNotFoundException.class})
	public ResponseEntity<String> userExceptionHandler()
	{
		return new ResponseEntity<String>("User ID not found, enter a valid User ID", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({UserNameExistsException.class})
	public ResponseEntity<String> userNameExceptionHandler()
	{
		return new ResponseEntity<String>("User name already exists, try registering using different username", HttpStatus.NOT_IMPLEMENTED);
	}
	
	@ExceptionHandler({CarNotFoundException.class})
	public ResponseEntity<String> carExceptionHandler()
	{
		return new ResponseEntity<String>("Car ID not found, enter a valid Car ID", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({PaymentNotFoundException.class})
	public ResponseEntity<String> paymentExceptionHandler()
	{
		return new ResponseEntity<String>("Payment ID not found, enter a valid Payment ID", HttpStatus.NOT_FOUND);
	}
}
