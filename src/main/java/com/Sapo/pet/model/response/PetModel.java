package com.Sapo.pet.model.response;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.Sapo.pet.entities.Pet;
import com.fasterxml.jackson.annotation.JsonFormat;


public class PetModel {
	
	private Integer id;
	private String name;
	private String tags;
	private String content;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date createdOn;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	private Date modifiedOn;
	
	private List<VariantModel> variants;
	private List<OptionModel> options;
	

	public PetModel() {
		variants = new ArrayList<VariantModel>();
		options = new ArrayList<OptionModel>();
	}

	public void setPetModel(Pet pet) {
		this.id = pet.getId();
		this.name = pet.getName();
		this.tags = pet.getTags();
		this.content = pet.getContent();
		this.createdOn = pet.getCreatedOn();
		this.modifiedOn = pet.getModifiedOn();
		variants = new ArrayList<VariantModel>();
		options = new ArrayList<OptionModel>();
	}
	
	public void addOptionModel(OptionModel optionModel) {
		options.add(optionModel);
	}
	
	public void addVariantModel(VariantModel variantModel) {
		variants.add(variantModel);
	}
	
	public List<OptionModel> getOptions() {
		return options;
	}
	public void setOptions(List<OptionModel> options) {
		this.options = options;
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public List<VariantModel> getVariants() {
		return variants;
	}

	public void setVariants(List<VariantModel> variants) {
		this.variants = variants;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	
}
