package com.Sapo.pet.model.response;

public class Errors {

	private String name;

	public Errors(String name) {
		this.name = name;
	}
	public static Errors nullName(String name) {
		Errors err = new Errors(name);
		return err;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	
}
