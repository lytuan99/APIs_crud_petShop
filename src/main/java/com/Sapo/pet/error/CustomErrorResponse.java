package com.Sapo.pet.error;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

public class CustomErrorResponse {

	private HttpStatus status;
	private List<String> errors;
	
	
	public CustomErrorResponse() {
	}
	
	
	public CustomErrorResponse(HttpStatus badRequest, List<String> errors) {
		this.status = badRequest;
		this.errors = errors;
	}
	
	public CustomErrorResponse(HttpStatus status, String error) {
		this.status = status;
		this.errors = Arrays.asList(error);
	}


	public HttpStatus getStatus() {
		return status;
	}
	public void setStatus(HttpStatus status) {
		this.status = status;
	}


	public List<String> getErrors() {
		return errors;
	}


	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
	
}
