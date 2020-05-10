package com.Sapo.pet.service.serviceInterface;

import java.util.List;

import com.Sapo.pet.model.request.PetModelRequest;
import com.Sapo.pet.model.response.PetModel;

public interface PetService {
	
	public List<PetModel> getAllPet();
	public PetModel getPetById(Integer id);
	public PetModel addPet(PetModelRequest petModelReq);
	public PetModel editPetById(Integer id, PetModelRequest petModelReq);
	public void deletePet(Integer id);
	
}
