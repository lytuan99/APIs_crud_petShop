package com.Sapo.pet.model.response;

import java.util.Date;
import java.util.List;

import com.Sapo.pet.entities.Option;
import com.Sapo.pet.entities.Variant;
import com.fasterxml.jackson.annotation.JsonFormat;

public class VariantModel {

	private Integer id;
	private String option1;
	private String option2;
	private String option3;
	private Float price;
	private Integer age;
	private String ageUnit;
	private Float weight;
	private String weightUnit;
	private Integer inventoryQuantity;
	private Integer position;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifiedOn;
	
	
	public VariantModel() {
		super();
	}


	public VariantModel(Variant variant, List<Option> options) {
		super();
		this.id = variant.getId();
		this.option1 = options.get(0).getValue();
		if(options.size() > 0)
			if(options.get(1) != null)
				this.option2 =  options.get(1).getValue();
		
		if(options.size() > 1)
			if(options.get(2) != null)
				this.option3 =  options.get(2).getValue();
		
		this.age = variant.getAge();
		this.ageUnit = variant.getAgeUnit();
		this.weight = variant.getWeight();
		this.weightUnit = variant.getWeightUnit();
		this.inventoryQuantity = variant.getInventoryQuantity();
		this.position = variant.getPosition();
		this.createdOn = variant.getCreatedOn();
		this.modifiedOn = variant.getModifiedOn();
		this.price = variant.getPrice();
		
	}
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
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
	public Date getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}
	public Date getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}


	public Float getPrice() {
		return price;
	}


	public void setPrice(Float price) {
		this.price = price;
	}


	public Integer getPosition() {
		return position;
	}


	public void setPosition(Integer position) {
		this.position = position;
	}
	
	
}
