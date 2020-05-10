package com.Sapo.pet.service.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.Sapo.pet.entities.Option;
import com.Sapo.pet.entities.Pet;
import com.Sapo.pet.entities.Variant;
import com.Sapo.pet.model.response.OptionModel;
import com.Sapo.pet.model.response.PetModel;
import com.Sapo.pet.model.response.VariantModel;
import com.Sapo.pet.repository.OptionRepository;
import com.Sapo.pet.repository.PetsRepository;
import com.Sapo.pet.repository.VariantRepository;

@Component
public class TranferDataToResponse {
	
	@Autowired
	PetsRepository petRepository;
	@Autowired
	OptionRepository optionRepository;
	@Autowired
	VariantRepository variantRepository;

	public PetModel getAllVariantOfPet(Pet pet) {
		PetModel petModel = new PetModel();
		petModel.setPetModel(pet);
		List<Variant> variants = petRepository.findAllVariantsByIdPetAndStatus(pet.getId(), true);
		putVariantIntoPetModelResponse(petModel, variants);
		return petModel;
	}

	
	public void putVariantIntoPetModelResponse(PetModel petModel, List<Variant> variants) {
		List<VariantModel> variantModels = new ArrayList<VariantModel>();
		List<OptionModel> optionModels = new ArrayList<OptionModel>();
		
		putVariantIntoVariantModelResponse(variants, variantModels, optionModels, petModel.getId());
		putOptionIntoPetModelResponse(optionModels, petModel);
		petModel.setVariants(variantModels);
	}
	
	public void putOptionIntoPetModelResponse(List<OptionModel> optionModels, PetModel petModel) {
		for (OptionModel optionModel : optionModels)
			if (optionModel.getName() != null)
				petModel.addOptionModel(optionModel);
	}
	
	public void putVariantIntoVariantModelResponse(List<Variant> variants, List<VariantModel> variantModels, List<OptionModel> optionModels, Integer idPet) {
		for (Variant variant : variants) { 
			
			List<Option> options = variantRepository.findAllOptionByIdVariant(variant.getId(), true);
			VariantModel variantModel = new VariantModel(variant, options);
			putOptionIntoOptionModelResponse(idPet, options, optionModels);
			variantModels.add(variantModel);
		}
	}

	// this function tranfer data from option and value to optionModel
	public void putOptionIntoOptionModelResponse(Integer petId, List<Option> options, List<OptionModel> optionModels) {
		for (Option option : options) {
			if (option.getName() != null &&
					!checkNameOptionIntoListModelOption(option.getName(), optionModels)) {
				OptionModel optionModel = new OptionModel(option, petId);
				optionModels.add(optionModel);
				
			}
			else if(option.getName() != null && 
					checkNameOptionIntoListModelOption(option.getName(), optionModels))
				addValueIntoListModelOption(option.getName(), option.getValue(), optionModels);
		}
	}
	
	private boolean checkNameOptionIntoListModelOption(String name, List<OptionModel> optionModels) {
		for (OptionModel optionModel : optionModels) {
			if(optionModel.getName().compareToIgnoreCase(name) == 0)
				return true;
		}
		return false;
	}
	
	
	private void addValueIntoListModelOption(String name, String value, List<OptionModel> optionModels) {
		for (OptionModel optionModel : optionModels) {
			if(optionModel.getName().compareToIgnoreCase(name) == 0) {
				if(!checkValueOptionIntoListModelOption(value, optionModel.getValues()))
					optionModel.addValue(value);
			}
		}
	}
	
	private boolean checkValueOptionIntoListModelOption(String valueOption, List<String> values) {
		for (String value : values) {
			if(value.compareToIgnoreCase(valueOption) == 0)
				return true;
		}
		return false;
	}

}
