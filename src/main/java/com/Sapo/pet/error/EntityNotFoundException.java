package com.Sapo.pet.error;

public class EntityNotFoundException extends RuntimeException{

	private Integer id;
	
	public EntityNotFoundException(Integer id, String entity) {
		super("not found id "+ entity +" with id: "+ id);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
