package com.Sapo.pet.error;

public class PetNotFoundException extends RuntimeException{

	private Integer id;
	
	public PetNotFoundException(Integer id) {
		super("not found id pet with id: "+ id);
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
