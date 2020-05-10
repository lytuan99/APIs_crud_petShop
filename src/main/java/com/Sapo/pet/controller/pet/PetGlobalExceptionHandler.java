package com.Sapo.pet.controller.pet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.Sapo.pet.error.CustomErrorResponse;
import com.Sapo.pet.error.EntityNotFoundException;
import com.Sapo.pet.error.IteratedOptionException;


@ControllerAdvice
public class PetGlobalExceptionHandler extends ResponseEntityExceptionHandler{

	@ExceptionHandler(EntityNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request, HttpServletResponse response) throws IOException {
		CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(IteratedOptionException.class)
	public ResponseEntity<CustomErrorResponse> customHandleIteratedOption(Exception ex, WebRequest request, HttpServletResponse response) throws IOException {
		CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers,
			HttpStatus status,
			WebRequest request) {
		
		List<String> errors = new ArrayList<String>();
		for (FieldError error : ex.getBindingResult().getFieldErrors()) {
			errors.add(error.getField() + ": " + error.getDefaultMessage());
		}
		
		for(ObjectError error : ex.getBindingResult().getGlobalErrors()) {
			errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
		}
		
		CustomErrorResponse errorResponse = new CustomErrorResponse(HttpStatus.BAD_REQUEST, errors);
		
		return handleExceptionInternal(ex, errorResponse,headers, errorResponse.getStatus(), request);
	}
}








