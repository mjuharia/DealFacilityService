package com.loan.service.DealFacilityService.exception;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.loan.service.DealFacilityService.User.UserNotFoundException;

@ControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ErrorDetails> handleAllException(Exception ex, WebRequest request) throws Exception{
		ErrorDetails lErrDet = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(lErrDet, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(UserNotFoundException.class)
	public final ResponseEntity<ErrorDetails> handleUserNotFoundException(Exception ex, WebRequest request) throws Exception{
		ErrorDetails lErrDet = new ErrorDetails(LocalDateTime.now(), ex.getMessage(), request.getDescription(false));
		return new ResponseEntity<ErrorDetails>(lErrDet, HttpStatus.NOT_FOUND);
	}
	
	
	protected ResponseEntity<Object> handleMethodArgumentNotValid(
			MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		String errorMsg = "";
		
		for (ObjectError err : ex.getAllErrors()) {
			errorMsg = errorMsg + err.getDefaultMessage() + " ";
		}
		
		ErrorDetails lErrDet = new ErrorDetails(LocalDateTime.now(), errorMsg, request.getDescription(false));
		
		return new ResponseEntity(lErrDet, HttpStatus.BAD_REQUEST);
	}
	
}
