package com.Sapo.pet.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Sapo.pet.entities.Option;
import com.Sapo.pet.entities.Pet;
import com.Sapo.pet.entities.Variant;
import com.Sapo.pet.error.PetNotFoundException;
import com.Sapo.pet.model.request.OptionModelRequest;
import com.Sapo.pet.model.request.PetModelRequest;
import com.Sapo.pet.model.request.VariantModelRequest;
import com.Sapo.pet.model.response.OptionModel;
import com.Sapo.pet.model.response.PetModel;
import com.Sapo.pet.model.response.VariantModel;
import com.Sapo.pet.notificationCode.SignalCode;
import com.Sapo.pet.repository.OptionRepository;
import com.Sapo.pet.repository.PetsRepository;
import com.Sapo.pet.repository.VariantRepository;
import com.Sapo.pet.service.serviceInterface.PetService;

@Service
@Transactional(rollbackFor = Exception.class)
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
				if(petModel == null)
					continue;
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
				throw new PetNotFoundException(id);
		PetModel petModel = new PetModel();
		petModel.setPetModel(pet);
		List<Variant> variants = petRepository.findAllVariantsByIdPetAndStatus(id, true);
		putVariantIntoPetModelResponse(petModel, variants);
		return petModel;
	}

	// this function tranfer data variant to variantModel
	private void putVariantIntoPetModelResponse(PetModel petModel, List<Variant> variants) {
		List<VariantModel> variantModels = new ArrayList<VariantModel>();
		OptionModel[] optionModels = new OptionModel[Variant.MAX_OPTION];

		for (int i = 0; i < optionModels.length; i++) {
			optionModels[i] = new OptionModel();
		}

		for (Variant variant : variants) { //
			VariantModel variantModel = new VariantModel(variant);
			List<Option> options = variantRepository.findAllOptionByIdVariant(variant.getId(), true);
			putOptionIntoOptionModelResponse(petModel.getId(), options, optionModels);
			variantModels.add(variantModel);
		}
		for (OptionModel optionModel : optionModels)
			if (optionModel.getName() != null)
				petModel.addOptionModel(optionModel);

		petModel.setVariants(variantModels);
	}

	// this function tranfer data option and value to optionModel
	private void putOptionIntoOptionModelResponse(Integer petId, List<Option> options, OptionModel[] optionModels) {
		int position = 0;
		for (Option option : options) { // this loop has a max element is MAX_OPTION
			if (option.getName() != null) {
				optionModels[position].setPet_id(petId);
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
	public SignalCode addPet(PetModelRequest petModelReq, PetModel petModel) {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

		Pet pet = new Pet();
		pet.setPet(pet, petModelReq, date, date);
		petRepository.save(pet);
		petModel.setPetModel(pet);
		if (petModelReq.getVariants() == null) {
			saveDefaultEntities(petModel, date);
			return SignalCode.SUCCESS;
		} else {
			return saveVariant(petModel, petModelReq.getVariants(), date);
		}
	}

	
	@Override
	public boolean editPetById(Integer id, PetModelRequest petModelReq, PetModel petModel)
			throws NotFoundException, javassist.NotFoundException {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Pet currentPet = petRepository.findByIdAndStatus(id, true);
		if (currentPet == null)
			return false;
		if (petModelReq.getName() != null) {
			currentPet.setName(petModelReq.getName());
		}
		if (petModelReq.getContent() != null) {
			currentPet.setContent(petModelReq.getContent());
		}
		if (petModelReq.getTags() != null) {
			currentPet.setTags(petModelReq.getTags());
		}
		petModel.setPetModelUpdate(id, currentPet.getName(), currentPet.getTags(), currentPet.getContent(), currentPet.getCreatedOn(), date);
		currentPet.setModifiedOn(date);
		petRepository.save(currentPet); 
		if (petModelReq.getVariants() == null) {
			return true;
		}
		if (petModelReq.getVariants() != null) {
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
// hàm này hiện tại ko trả về response cho tất cả các varaint của pet,.. mới chỉ trả về các variant mới hoặc được update
	private boolean saveVariantAndOptionUpdate(PetModel petModel, List<VariantModelRequest> variantModelReqs, Date date) throws javassist.NotFoundException {
		List<VariantModelRequest> newVariantMR = new ArrayList<VariantModelRequest>(); // include the new variants (have id == null)
		for (VariantModelRequest variantModelReq : variantModelReqs) {
			if (variantModelReq.getId() == null) {
				newVariantMR.add(variantModelReq);
				continue;
			}
			Variant variant = variantRepository.findByIdAndStatus(variantModelReq.getId(), petModel.getId(), true);
			if(getVariantFromVariantModelReqUpdate(variant, variantModelReq, petModel, date))
				continue;
			
		}
		if (newVariantMR.size() != 0)
			saveVariant(petModel, variantModelReqs, date);
		return true;
	}
	
	private boolean getVariantFromVariantModelReqUpdate(Variant variant, VariantModelRequest variantModelReq, PetModel petModel,Date date) throws javassist.NotFoundException {
		if (variant == null) {
			throw new javassist.NotFoundException("Not found id variant");
		}
		if (variantModelReq.getPrice() != null)
			variant.setPrice(variantModelReq.getPrice());
		if (variantModelReq.getAge() != null)
			variant.setAge(variantModelReq.getAge());
		if (variantModelReq.getAgeUnit() != null)
			variant.setAgeUnit(variantModelReq.getAgeUnit());
		if (variantModelReq.getInventoryQuantity() != null)
			variant.setInventoryQuantity(variantModelReq.getInventoryQuantity());
		if (variantModelReq.getWeight() != null)
			variant.setWeight(variantModelReq.getWeight());
		if (variantModelReq.getWeightUnit() != null)
			variant.setWeightUnit(variantModelReq.getWeightUnit());
		if (variantModelReq.getOptions() == null) {
			variant.setModifiedOn(date);
			variantRepository.save(variant);
			VariantModel variantModel = new VariantModel(variant);
			petModel.addVariantModel(variantModel);
			return true;
		} else
			saveOptionToUpdate(petModel, variant, variantModelReq, date);
		return false;
	}

	public void saveOptionToUpdate(PetModel petModel, Variant variant, VariantModelRequest variantModelReq, Date date) {
		String[] nameOptions = new String[3];
		String[] values = new String[3];
		int i = 0;
		for (OptionModelRequest oMR : variantModelReq.getOptions()) {
			nameOptions[i] = oMR.getName();
			values[i] = oMR.getValue();
			i++;
		}
		variant.setOption1(values[0]);
		if (values[1] != null)
			variant.setOption2(values[1]);
		if (values[2] != null)
			variant.setOption3(values[2]);

		variant.setModifiedOn(date);
		variantRepository.save(variant);
		VariantModel variantModel = new VariantModel(variant);
		petModel.addVariantModel(variantModel);

		OptionModel[] optionModels = new OptionModel[3];
		List<Option> currentOptions = variantRepository.findAllOptionByIdVariant(variant.getId(), true);
		i = 0;
		while (nameOptions[i] != null && values[i] != null) {
			currentOptions.get(i).setName(nameOptions[i]);
			currentOptions.get(i).setModifiedOn(date);
			currentOptions.get(i).setValue(values[i]);
			optionRepository.save(currentOptions.get(i));

			optionModels[i] = new OptionModel();
			optionModels[i].setOptionModel(nameOptions[i], values[i], i + 1, petModel.getId());
			petModel.addOptionModel(optionModels[i]);
			i++;
			if (currentOptions.size() == i)
				break;
		}
	}

	

	private SignalCode saveVariant(PetModel petModel, List<VariantModelRequest> variantModelReqs, Date date) {
		OptionModel[] optionModels = new OptionModel[Variant.MAX_OPTION];

		for (VariantModelRequest variantModelReq : variantModelReqs) {

			Variant variant = new Variant();
			saveVariantSingle(variant, petModel, variantModelReq, date);

			if (variantModelReq.getOptions() == null) {
				return saveOptionDefaultWhenOptionRequestNull(variant, petModel, optionModels, date);
			} else {
				return saveOptionWhenOptionRequestNotNull(variant, petModel, variantModelReq, optionModels, date);
			}
		}
		return null;
	} 

	private void saveVariantSingle(Variant variant, PetModel petModel, VariantModelRequest variantModelReq, Date date) {
		variant.setVariant(variantModelReq, petModel.getId(), date, date);
		variantRepository.save(variant);

		VariantModel variantModel = new VariantModel(variant);
		petModel.addVariantModel(variantModel);
	}

	private SignalCode saveOptionDefaultWhenOptionRequestNull(Variant variant, PetModel petModel,
			OptionModel[] optionModels, Date date) {
		Option option = new Option();
		option.setOptionDefault(option, date, variant.getId()); // set default option
		optionRepository.save(option);

		optionModels[0] = new OptionModel(option, petModel.getId());
		optionModels[0].addValue("Default Title");
		petModel.addOptionModel(optionModels[0]);
		return SignalCode.SUCCESS;
	}

	private SignalCode saveOptionWhenOptionRequestNotNull(Variant variant, PetModel petModel,
			VariantModelRequest variantModelReq, OptionModel[] optionModels, Date date) {
		String[] nameOptions = new String[Variant.MAX_OPTION];
		String[] values = new String[Variant.MAX_OPTION];
		int i = 0;
		for (OptionModelRequest oMR : variantModelReq.getOptions()) {
			nameOptions[i] = oMR.getName();
			values[i] = oMR.getValue();
			if (i > 0) {
				if (nameOptions[i - 1].compareToIgnoreCase(nameOptions[i]) == 0
						|| values[i - 1].compareToIgnoreCase(values[i]) == 0) // nếu bị lặp option trong cùng một
																				// variant
					return SignalCode.ITERATED_OPTION;
			}
			optionModels[i] = new OptionModel();
			optionModels[i].setOptionModel(nameOptions[i], values[i], i + 1, petModel.getId());
			i++;
		}
		for (int j = 0; j < i; j++)
			if (optionModels[j].getName() != null)
				petModel.addOptionModel(optionModels[j]);

		saveOption(nameOptions, values, variant.getId(), date);
		return SignalCode.SUCCESS;
	}

	public void saveOption(String[] nameOptions, String[] values, Integer idVariant, Date date) {
		for (int i = 0; i < nameOptions.length; i++) {
			Option option = new Option();
			option.setOption(option, nameOptions[i], values[i], i + 1, date, idVariant);
			optionRepository.save(option);
		}
	}

	private void saveDefaultEntities(PetModel petModel, Date date) {
		Variant variant = new Variant();
		Option option = new Option();

		variant.setVariantDefault(variant, petModel.getId(), date, date); // set default variant
		variantRepository.save(variant);
		VariantModel variantModel = new VariantModel(variant);
		petModel.addVariantModel(variantModel);

		option.setOptionDefault(option, date, variant.getId()); // set default option
		optionRepository.save(option);
		for (int i = 1; i < 3; i++) {
			option = new Option();

		}
		OptionModel optionModel = new OptionModel(option, "Default Title", petModel.getId());
		petModel.addOptionModel(optionModel);
	}

//	private boolean isTheSameOption(String nam)

}
