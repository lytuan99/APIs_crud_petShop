package com.Sapo.pet.service.serviceInterface;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.Sapo.pet.model.request.PetModelRequest;
import com.Sapo.pet.model.response.PetModel;

public interface PetService {
	
	public List<PetModel> getAllPet();
	public PetModel getPetById(Integer id);
	public PetModel getPetByName(String name);
	public boolean addPet(PetModelRequest petModelReq, PetModel petModel);
	public boolean editPetById(Integer id, PetModelRequest petModelReq, PetModel petModel) throws NotFoundException;
	public boolean deletePet(Integer id);
	
}
