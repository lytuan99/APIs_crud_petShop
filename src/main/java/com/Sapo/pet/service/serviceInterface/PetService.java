package com.Sapo.pet.service.serviceInterface;

import java.util.List;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

import com.Sapo.pet.model.request.PetModelRequest;
import com.Sapo.pet.model.response.PetModel;
import com.Sapo.pet.notificationCode.SignalCode;

public interface PetService {
	
	public List<PetModel> getAllPet();
	public PetModel getPetById(Integer id);
	public PetModel getPetByName(String name);
	public SignalCode addPet(PetModelRequest petModelReq, PetModel petModel);
	public boolean editPetById(Integer id, PetModelRequest petModelReq, PetModel petModel) throws NotFoundException, javassist.NotFoundException;
	public boolean deletePet(Integer id);
	
}
