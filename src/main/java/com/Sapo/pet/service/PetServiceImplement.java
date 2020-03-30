package com.Sapo.pet.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.Sapo.pet.entities.Option;
import com.Sapo.pet.entities.Pet;
import com.Sapo.pet.entities.Variant;
import com.Sapo.pet.model.request.OptionModelRequest;
import com.Sapo.pet.model.request.PetModelRequest;
import com.Sapo.pet.model.request.VariantModelRequest;
import com.Sapo.pet.model.response.OptionModel;
import com.Sapo.pet.model.response.PetModel;
import com.Sapo.pet.model.response.VariantModel;
import com.Sapo.pet.repository.OptionRepository;
import com.Sapo.pet.repository.PetsRepository;
import com.Sapo.pet.repository.VariantRepository;
import com.Sapo.pet.service.serviceInterface.PetService;

@Service
public class PetServiceImplement implements PetService {

	@Autowired
	PetsRepository petRepository;
	@Autowired
	OptionRepository optionRepository;
	@Autowired
	VariantRepository variantRepository;

	@Override
	public List<PetModel> getAllPet() {
		List<Pet> pets = petRepository.findAllPets();
		List<PetModel> petModels = new ArrayList<PetModel>();
		try {
			for (Pet pet : pets) {
				PetModel petModel = getPetById(pet.getId());
				petModels.add(petModel);
			}
			return petModels;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("not available");
			return null;
		}

	}

	@Override
	public PetModel getPetById(Integer id) {
		Pet pet = petRepository.findByIdAndStatus(id, true);
		if (pet == null)
			return null;
		PetModel petModel = new PetModel();
		petModel.setPetModel(pet);
		List<Variant> variants = petRepository.findAllVariantsByIdPetAndStatus(id, true);
		putVariantIntoPetModelResponse(petModel, variants);
		return petModel;
	}
	
	// this function tranfer data variant to variantModel
	private void putVariantIntoPetModelResponse(PetModel petModel, List<Variant> variants ) {
		List<VariantModel> variantModels = new ArrayList<VariantModel>();
		OptionModel[] optionModels = new OptionModel[3];
		optionModels[0] = new OptionModel();
		optionModels[1] = new OptionModel();
		optionModels[2] = new OptionModel();
		for (Variant variant : variants) {							// 
			VariantModel variantModel = new VariantModel(variant);
			List<Option> options = variantRepository.findAllOptionByIdVariant(variant.getId(), true);
			putOptionIntoPetModelResponse(petModel, options, optionModels);
			variantModels.add(variantModel);
		}
		if(optionModels[0].getName() != null)
			petModel.addOptionModel(optionModels[0]);
		if(optionModels[1].getName() != null)
			petModel.addOptionModel(optionModels[1]);
		if(optionModels[2].getName() != null)
			petModel.addOptionModel(optionModels[2]);
		
		petModel.setVariants(variantModels); 
	}
	
	// this function tranfer data option and value to optionModel
	private void putOptionIntoPetModelResponse( PetModel petModel, List<Option> options, OptionModel[] optionModels) {
		int position = 0;
		for (Option option : options) {    //this loop has a max element is 3
			if(option.getName() != null) {
				optionModels[position].setPet_id(petModel.getId());
				optionModels[position].setName(option.getName());
				optionModels[position].setPosition(option.getPosition());
				optionModels[position].addValue(option.getValue());  
				position++;
			}
		}

	}
	

	@Override
	public PetModel getPetByName(String name) {

		return null;
	}

	@Override
	public boolean addPet(PetModelRequest petModelReq, PetModel petModel) {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		
		if (petModelReq.getName() == null)
			return false;
		Pet pet = new Pet();
		setPet(pet, petModelReq, date, date);
		petRepository.save(pet);
		petModel.setPetModel(pet);
		if (petModelReq.getVariants() == null) {
			saveDefaultEntities(petModel, date);
			return true;
		} else {
			saveVariant(petModel, petModelReq.getVariants(), pet.getId(), date);
			return true;
		}
	}
	
	

	private boolean saveVariantAndOptionUpdate(PetModel petModel, List<VariantModelRequest> variantModelReqs, Date date) {
		List<VariantModelRequest> newVariantMR = new ArrayList<VariantModelRequest>(); 		// include the new variants (have id == null)
		for (VariantModelRequest variantModelReq : variantModelReqs) {
			if(variantModelReq.getId() == null) {
				newVariantMR.add(variantModelReq);
				continue;
			}
			Variant variant = variantRepository.findByIdAndStatus(variantModelReq.getId(), petModel.getId(), true);
			VariantModel variantModel;
			if(variantModelReq.getAge() != null)
				variant.setAge(variantModelReq.getAge());
			if(variantModelReq.getAgeUnit() != null)
				variant.setAgeUnit(variantModelReq.getAgeUnit());
			if(variantModelReq.getInventoryQuantity() != null)
				variant.setInventoryQuantity(variantModelReq.getInventoryQuantity());
			if(variantModelReq.getWeight() != null)
				variant.setWeight(variantModelReq.getWeight());
			if(variantModelReq.getWeightUnit() != null)
				variant.setWeightUnit(variantModelReq.getWeightUnit());
			
			
			
			if(variantModelReq.getOptions() == null) {
				variant.setModifiedOn(date);
				variantRepository.save(variant);
				variantModel = new VariantModel(variant);
				petModel.addVariantModel(variantModel);
				continue;
			}
			else 
				saveOptionToUpdate(petModel, variant, variantModelReq, date);
				
		}
		if(newVariantMR.size() != 0)
			saveVariant( petModel, variantModelReqs, petModel.getId(), date);
		return true;
	}

	public void saveOptionToUpdate(PetModel petModel,Variant variant, VariantModelRequest variantModelReq, Date date) {
		String[] nameOptions = new String[3];
		String[] values = new String[3];
		int i = 0;
		for (OptionModelRequest oMR : variantModelReq.getOptions()) {
			nameOptions[i] = oMR.getName();
			values[i] = oMR.getValue();
			i++;
		}
		variant.setOption1(values[0]);
		if(values[1] != null)
			variant.setOption2(values[1]);
		if(values[2] != null)
			variant.setOption3(values[2]);
		
		variant.setModifiedOn(date);
		variantRepository.save(variant);
		VariantModel variantModel = new VariantModel(variant);
		petModel.addVariantModel(variantModel);
		
		OptionModel[] optionModels = new OptionModel[3];
		List<Option> currentOptions = variantRepository.findAllOptionByIdVariant(variant.getId(), true);
		i = 0;
		while(nameOptions[i] != null && values[i] != null) {
			currentOptions.get(i).setName(nameOptions[i]);
			currentOptions.get(i).setModifiedOn(date); 
			currentOptions.get(i).setValue(values[i]);
			optionRepository.save(currentOptions.get(i));
			
			optionModels[i] = new OptionModel();
			optionModels[i].setOptionModel(nameOptions[i], values[i], i+1, petModel.getId());
			petModel.addOptionModel(optionModels[i]);
			i++;
			if(currentOptions.size() == i)
				break;
		}
	}
	
	
	@Override 
	public boolean editPetById(Integer id, PetModelRequest petModelReq, PetModel petModel) throws NotFoundException {

		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		
		Pet currentPet = petRepository.findByIdAndStatus(id, true);
		
		if (currentPet == null)
			return false;
		
		if(petModelReq.getName() != null) {
			currentPet.setName(petModelReq.getName());
		}
		if(petModelReq.getContent() != null) {
			currentPet.setContent(petModelReq.getContent());
		}
		if(petModelReq.getTags() != null) {
			currentPet.setTags(petModelReq.getTags());
		}
		petModel.setPetModelUpdate(id,currentPet.getName(), currentPet.getTags(), currentPet.getContent(), currentPet.getCreatedOn(), date);
		currentPet.setModifiedOn(date);
		petRepository.save(currentPet);
		
		if(petModelReq.getVariants() == null) {
			return true;
		}
		if(petModelReq.getVariants() != null) {
			return saveVariantAndOptionUpdate(petModel, petModelReq.getVariants(), date);
		}
		return false;
	}

	@Override
	public boolean deletePet(Integer id) {
		Optional<Pet> currentPet = petRepository.findById(id);
		if (!currentPet.isPresent())
			return false;
		else {
			Calendar cal = Calendar.getInstance();
			currentPet.get().setModifiedOn(cal.getTime());
			currentPet.get().setStatus(false);
			petRepository.save(currentPet.get());
			return false;
		}
	}

	private void saveVariant(PetModel petModel, List<VariantModelRequest> variantModelReqs, Integer petId,  Date date ) {
		OptionModel[] optionModels = new OptionModel[3];
		optionModels[0] = new OptionModel();
		optionModels[1] = new OptionModel();
		optionModels[2] = new OptionModel();
		for (VariantModelRequest variantModelReq : variantModelReqs) {
			
			Variant variant = new Variant();
			setVariant(variant, variantModelReq, petId, date, date);
			variantRepository.save(variant);
			VariantModel variantModel = new VariantModel(variant);
			petModel.addVariantModel(variantModel);				
			
			if(variantModelReq.getOptions() == null) {   // ĐỂ Ý CHỖ NÀY: nếu chuyển thành mặc định luôn lưu 3 option thì phải sửa ở đây
				
				Option option = new Option();
				setOptionDefault(option, date, variant.getId()); // set default option
				optionRepository.save(option);
				
				
				optionModels[0] = new OptionModel(option, petModel.getId());
				optionModels[0].addValue("Default Title");
				petModel.addOptionModel(optionModels[0]);				
			}
			else {
				String[] nameOptions = new String[3];
				String[] values = new String[3];
				int i = 0;
				for (OptionModelRequest oMR : variantModelReq.getOptions()) {
					nameOptions[i] = oMR.getName();
					values[i] = oMR.getValue();
					optionModels[i].setOptionModel(nameOptions[i], values[i], i+1, petModel.getId());
					i++;
				}
				if(optionModels[0].getName() != null)
					petModel.addOptionModel(optionModels[0]);
				if(optionModels[1].getName() != null)
					petModel.addOptionModel(optionModels[1]);
				if(optionModels[2].getName() != null)
					petModel.addOptionModel(optionModels[2]);
				
				saveOption(nameOptions, values, variant.getId(), date, variantModel);
			}
		}
	}

	public void saveOption(String[] nameOptions, String[] values, Integer idVariant, Date date, VariantModel variantModel) {
		for (int i = 0; i < nameOptions.length; i++) {
			Option option = new Option();
			setOption(option, nameOptions[i], values[i], i + 1, date, idVariant);
			optionRepository.save(option);
		}
	}
	
	
	private void saveDefaultEntities(PetModel petModel, Date date) {
		Variant variant = new Variant();
		Option option = new Option();
		
		setVariantDefault(variant, petModel.getId(), date, date); // set default variant
		variantRepository.save(variant);
		VariantModel variantModel = new VariantModel(variant);
		petModel.addVariantModel(variantModel);

		setOptionDefault(option, date, variant.getId()); // set default option
		optionRepository.save(option);
		for(int i = 1; i < 3; i++) {
			option = new Option();
			
		}
		OptionModel optionModel = new OptionModel(option, "Default Title", petModel.getId());
		petModel.addOptionModel(optionModel);
	}
	
	private void setPet(Pet pet,PetModelRequest petModelReq, Date createD, Date modifiedD) {
		pet.setName(petModelReq.getName());
		pet.setTags(petModelReq.getTags());
		pet.setContent(petModelReq.getContent());
		pet.setCreatedOn(createD);
		pet.setModifiedOn(modifiedD);
		pet.setStatus(true);
	}

	private void  setOptionDefault(Option option, Date date, Integer idVariant) {
		option.setName("Default"); 
		option.setValue("Default Title");
		option.setPosition(1); 
		option.setCreatedOn(date); 
		option.setModifiedOn(date); 
		option.setStatus(true); 
		option.setIdVariant(idVariant); 
	}

	private void  setOption(Option option, String name, String value, Integer position, Date date,Integer idVariant  ) {
		option.setName(name); 
		option.setValue(value);
		option.setPosition(position); 
		option.setCreatedOn(date); 
		option.setModifiedOn(date); 
		option.setStatus(true); 
		option.setIdVariant(idVariant); 
	
	}
	
	// default variant
	private void setVariantDefault(Variant variant, Integer id, Date date1, Date date2) {
		variant.setOption1("default title"); 
		variant.setOption2(null); 
		variant.setOption3(null); 
		variant.setAge(0);
		variant.setAgeUnit(null); 
		variant.setWeight((float) 0.0); 
		variant.setWeightUnit(null); 
		variant.setInventoryQuantity(0);
		variant.setCreatedOn(date1); 
		variant.setModifiedOn(date2); 
		variant.setStatus(true); 
		variant.setIdPet(id); 
	}
		
	public void  setVariant(Variant variant, VariantModelRequest variantModelReq, Integer idPet , Date date1, Date date2) {
		String[] valueOptions = new String[3];
		int i = 0;
		if(variantModelReq.getOptions() == null) {
			valueOptions[0] = "Default Title";
			valueOptions[1] = null;
			valueOptions[2] = null;
		}
		else {
			for (OptionModelRequest oMR : variantModelReq.getOptions()) {			// get value from request
				valueOptions[i] = oMR.getValue();
				i++;
			}
		}
		
		variant.setOption1( valueOptions[0]);
		variant.setOption2( valueOptions[1]);
		variant.setOption3( valueOptions[2]);
		variant.setAge(variantModelReq.getAge());
		variant.setAgeUnit(variantModelReq.getAgeUnit()); 
		variant.setWeight(variantModelReq.getWeight()); 
		variant.setWeightUnit(variantModelReq.getWeightUnit()); 
		variant.setInventoryQuantity(variantModelReq.getInventoryQuantity());
		variant.setCreatedOn(date1); 
		variant.setModifiedOn(date2); 
		variant.setStatus(true); 
		variant.setIdPet(idPet); 
	}
}
