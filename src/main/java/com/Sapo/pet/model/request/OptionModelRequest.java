package com.Sapo.pet.model.request;

import org.springframework.validation.annotation.Validated;

@Validated
public class OptionModelRequest {

	private String name;
	private String value;
	
	
	public OptionModelRequest(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	
	
}
