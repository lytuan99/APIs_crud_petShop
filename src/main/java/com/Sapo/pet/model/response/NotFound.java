package com.Sapo.pet.model.response;

public class NotFound {

	private String error;
	
	public NotFound(String error) {
		this.setError(error);
	}
	
	public static NotFound notFound(String message) {
		
		NotFound notFound = new NotFound(message);
		return notFound;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
