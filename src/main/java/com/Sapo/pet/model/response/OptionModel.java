package com.Sapo.pet.model.response;

import java.util.ArrayList;
import java.util.List;

import com.Sapo.pet.entities.Option;

public class OptionModel {

	private Integer pet_id;
	private String name;
	private Integer position;
	private List<String> values;
	
	
	

	public OptionModel(Option option, Integer idPet) {
		this.pet_id = idPet;
		this.name = option.getName();
		this.position = option.getPosition();
	}
	
	public OptionModel(Option option,String value, Integer idPet) {
		this.pet_id = idPet;
		this.values.add(value);
		this.name = option.getName();
		this.position = option.getPosition();
	}
	
	public void setOptionModel(String name, String value, Integer position, Integer idPet) {
		this.pet_id = idPet;
		this.position = position;
		this.name = name;
		this.values.add(value);
		}
	
	public OptionModel() {
		values = new ArrayList<String>();
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public void addValue(String value) {
		if(values == null)
			values = new ArrayList<String>();
		values.add(value);
	}

	public List<String> getValues() {
		return values;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getPosition() {
		return position;
	}
	public void setPosition(Integer position) {
		this.position = position;
	}
//	public String[] getValues() {
//		return values;
//	}
//	public void setValues(String[] values) {
//		this.values = values;
//	}

	public Integer getPet_id() {
		return pet_id;
	}

	public void setPet_id(Integer pet_id) {
		this.pet_id = pet_id;
	}
	
	
}
