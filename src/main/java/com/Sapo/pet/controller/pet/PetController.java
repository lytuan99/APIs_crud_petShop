package com.Sapo.pet.controller.pet;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sapo.pet.model.request.PetModelRequest;
import com.Sapo.pet.model.response.Notification;
import com.Sapo.pet.model.response.PetModel;
import com.Sapo.pet.service.serviceInterface.PetService;

@RestController
@RequestMapping("/api")
@Validated
public class PetController {
	
	@Autowired private PetService petService;

	@GetMapping(path = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PetModel> getAllPet(){
		return petService.getAllPet();
	}
	
	@GetMapping(path = "/pets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<PetModel> getPetbyId(@PathVariable("id") Integer id){
		PetModel petModel = petService.getPetById(id);
		if(petModel == null)
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(petModel, HttpStatus.OK);
	}
	
	@PostMapping(path = "/pets", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> createPet(@Valid @RequestBody PetModelRequest petModelReq){
		PetModel petModel = petService.addPet(petModelReq);
			return new ResponseEntity<>(petModel, HttpStatus.OK);
	}
	
	@PutMapping(path = "/pets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editPetbyId(@PathVariable("id") Integer id, @RequestBody PetModelRequest petModelReq) {
		PetModel petModel = petService.editPetById(id, petModelReq);
		return new ResponseEntity<>(petModel, HttpStatus.OK);
	}
	
	@DeleteMapping(path = "/pets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Notification> deletePetbyId(@PathVariable("id") Integer id){
		petService.deletePet(id);
			return ResponseEntity.ok(new Notification(true, "Delete successfully"));
		
	}
	
}
