package com.Sapo.pet.model.response;

public class Errors {

	private String error;

	public Errors(String error) {
		this.setError(error);
	}
	public static Errors message(String name) {
		Errors err = new Errors(name);
		return err;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
	
	
	
	
}
