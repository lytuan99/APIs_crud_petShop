package com.Sapo.pet.controller.pet;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
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
import com.Sapo.pet.model.response.Errors;
import com.Sapo.pet.model.response.NotFound;
import com.Sapo.pet.model.response.PetModel;
import com.Sapo.pet.model.response.RestResponse;
import com.Sapo.pet.notificationCode.SignalCode;
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
		PetModel petModel = new PetModel();
		SignalCode signal = petService.addPet(petModelReq,petModel );
		if(signal == SignalCode.ITERATED_OPTION)
			return new ResponseEntity<>(Errors.message("option is iterated!!"), HttpStatus.BAD_REQUEST);
		else
			return new ResponseEntity<>(petModel, HttpStatus.OK);
	}
	
	@PutMapping(path = "/pets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Object> editPetbyId(@PathVariable("id") Integer id, @RequestBody PetModelRequest petModelReq) throws javassist.NotFoundException{
		PetModel petModel = new PetModel();
		boolean check = false;
		try {
			check = petService.editPetById(id, petModelReq, petModel);
			
		} catch (NotFoundException e) {
			return new ResponseEntity<>(NotFound.notFound("not found id catch!"), HttpStatus.BAD_REQUEST);
		}
		if(check == true)
			return new ResponseEntity<>(petModel, HttpStatus.OK);
		else {
			return new ResponseEntity<>(NotFound.notFound("not found id!"), HttpStatus.BAD_REQUEST);
		}
			
	}
	
	@DeleteMapping(path = "/pets/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestResponse> deletePetbyId(@PathVariable("id") Integer id){
		if(petService.deletePet(id) == true)
			return ResponseEntity.ok(RestResponse.success("deleted successful!"));
		else
			return ResponseEntity.ok(RestResponse.failed("deleted failed!"));
	}
	
}
