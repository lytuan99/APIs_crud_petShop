package com.Sapo.pet.model.request;

import org.springframework.validation.annotation.Validated;

/**
 * VariantModelRequest get request from client 
 * include: value of option and name of option and the others
 * after that 
 * tranform to entities Variant, Option and OptionValue
 * 
 * */

@Validated
public class VariantModelRequest {

	private Integer id;
	private OptionModelRequest[] options;
	private Integer age;
	private String ageUnit;
	private Float weight;
	private String weightUnit;
	private Integer inventoryQuantity;
	private Float price;
	

	public Integer getAge() {
		return age;
	}
	public void setAge(Integer age) {
		this.age = age;
	}
	public String getAgeUnit() {
		return ageUnit;
	}
	public void setAgeUnit(String ageUnit) {
		this.ageUnit = ageUnit;
	}
	public Float getWeight() {
		return weight;
	}
	public void setWeight(Float weight) {
		this.weight = weight;
	}
	public String getWeightUnit() {
		return weightUnit;
	}
	public void setWeightUnit(String weightUnit) {
		this.weightUnit = weightUnit;
	}
	public Integer getInventoryQuantity() {
		return inventoryQuantity;
	}
	public void setInventoryQuantity(Integer inventoryQuantity) {
		this.inventoryQuantity = inventoryQuantity;
	}

	public OptionModelRequest[] getOptions() {
		return options;
	}
	public void setOptions(OptionModelRequest[] options) {
		this.options = options;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Float getPrice() {
		return price;
	}
	public void setPrice(Float price) {
		this.price = price;
	}

	
}
