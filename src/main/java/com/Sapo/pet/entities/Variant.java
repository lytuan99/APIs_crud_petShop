package com.Sapo.pet.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.Sapo.pet.model.request.OptionModelRequest;
import com.Sapo.pet.model.request.VariantModelRequest;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "variants")
public class Variant extends BaseEntity implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "option1", length = 45)
	private String option1;
	@Column(name = "option2", length = 45)
	private String option2;
	@Column(name = "option3", length = 45)
	private String option3;
	@Column(name = "age", length = 4)
	private Integer age;
	@Column(name = "age_unit", length = 10)
	private String ageUnit;
	@Column(name = "weight")
	private Float weight;
	@Column(name = "weight_unit", length = 5)
	private String weightUnit;
	@Column(name = "inventory_quantity", length = 10)
	private Integer inventoryQuantity;
	
	@Column(name = "created_on")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@Column(name = "modified_on")
	private Date modifiedOn;
	@Column(name = "status")
	private boolean status;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "pet_id")
//	private Pet pet;
	@Column(name = "pet_id")
	private Integer idPet;
	
//	@OneToMany(cascade = CascadeType.ALL, mappedBy = "variant" )
//	private List<Option> options;
	
	
	
	public String getOption1() {
		return option1;
	}
	public void setOption1(String option1) {
		this.option1 = option1;
	}
	public String getOption2() {
		return option2;
	}
	public void setOption2(String option2) {
		this.option2 = option2;
	}
	public String getOption3() {
		return option3;
	}
	public void setOption3(String option3) {
		this.option3 = option3;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
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
	public Integer getIdPet() {
		return idPet;
	}
	public void setIdPet(Integer idPet) {
		this.idPet = idPet;
	}
	

}
