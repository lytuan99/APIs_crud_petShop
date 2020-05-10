package com.Sapo.pet.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.Sapo.pet.entities.Option;
import com.Sapo.pet.entities.Pet;
import com.Sapo.pet.entities.Variant;
import com.Sapo.pet.error.EntityNotFoundException;
import com.Sapo.pet.error.IteratedOptionException;
import com.Sapo.pet.model.request.OptionModelRequest;
import com.Sapo.pet.model.request.PetModelRequest;
import com.Sapo.pet.model.request.VariantModelRequest;
import com.Sapo.pet.model.response.PetModel;
import com.Sapo.pet.repository.OptionRepository;
import com.Sapo.pet.repository.PetsRepository;
import com.Sapo.pet.repository.VariantRepository;
import com.Sapo.pet.service.serviceInterface.PetService;
import com.Sapo.pet.service.util.TranferDataToResponse;

@Service
@Transactional(rollbackFor = Exception.class)
public class PetServiceImplement implements PetService {

	@Autowired
	PetsRepository petRepository;
	@Autowired
	OptionRepository optionRepository;
	@Autowired
	VariantRepository variantRepository;
	
	@Autowired
	private TranferDataToResponse tranfer;

	@Override
	public List<PetModel> getAllPet() {
		List<Pet> pets = petRepository.findAllPets();
		List<PetModel> petModels = new ArrayList<PetModel>();
		for (Pet pet : pets) {
			if (pet.isStatus()) {
				PetModel petModel = tranfer.getAllVariantOfPet(pet);
				petModels.add(petModel);
			}
		}
		return petModels;
	}

	@Override
	public PetModel getPetById(Integer id) {
		Pet pet = petRepository.findByIdAndStatus(id, true);
		if (pet == null)
			throw new EntityNotFoundException(id, "Pet");
		PetModel petModel = tranfer.getAllVariantOfPet(pet);
		return petModel;
	}

	
	@Override
	public PetModel addPet(PetModelRequest petModelReq) {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();

		Pet pet = new Pet();
		pet.setPet(pet, petModelReq, date, date);
		petRepository.save(pet);
		if (petModelReq.getVariants() == null) {
			saveDefaultEntities(pet.getId(), date);
			return getPetById(pet.getId());
		} else {
			int positionInitial = 1;
			saveVariant(pet.getId(), petModelReq.getVariants(), date, positionInitial);
			return getPetById(pet.getId());
		}
	}

	@Override
	public PetModel editPetById(Integer id, PetModelRequest petModelReq) {
		Calendar cal = Calendar.getInstance();
		Date date = cal.getTime();
		Pet currentPet = petRepository.findByIdAndStatus(id, true);
		if (currentPet == null)
			throw new EntityNotFoundException(id, "Pet");
		if (petModelReq.getName() != null) {
			currentPet.setName(petModelReq.getName());
		}
		if (petModelReq.getContent() != null) {
			currentPet.setContent(petModelReq.getContent());
		}
		if (petModelReq.getTags() != null) {
			currentPet.setTags(petModelReq.getTags());
		}
		
		currentPet.setModifiedOn(date);
		petRepository.save(currentPet);
		
		if (petModelReq.getVariants() == null) {
			return getPetById(id);
		} else {
			saveVariantAndOptionUpdate(id, petModelReq.getVariants(), date);
			return getPetById(id);
		}

	}

	@Override
	public void deletePet(Integer id) {
		Pet currentPet = petRepository.findByIdAndStatus(id, true);
		if (currentPet == null)
			throw new EntityNotFoundException(id, "Pet");
		else {
			Calendar cal = Calendar.getInstance();
			Date date = cal.getTime();
			setVariantStatus(currentPet.getId(), date, false);  // delete all variant and option of pet
			
			currentPet.setModifiedOn(date);
			currentPet.setStatus(false);
			petRepository.save(currentPet);
		}
	}
	
	public void setVariantStatus(Integer idPet, Date date, Boolean status) {
		List<Variant> currentVariants = petRepository.findAllVariantsByIdPetAndStatus(idPet, true);
		for (Variant curVariant : currentVariants) {
			List<Option> currentOptions = variantRepository.findAllOptionByIdVariant(curVariant.getId(), true);
			setOptionStatus(currentOptions, date, status);
			
			curVariant.setStatus(false);
			curVariant.setModifiedOn(date);
			variantRepository.save(curVariant);
		}
	}
	
	private void setOptionStatus(List<Option> currentOptions, Date date, Boolean status) {
		for (Option curOption : currentOptions) {
			curOption.setStatus(false);
			curOption.setModifiedOn(date);
			optionRepository.save(curOption);
		}
	}

	private void saveVariantAndOptionUpdate(Integer idPet, List<VariantModelRequest> variantModelReqs, Date date) {
		List<VariantModelRequest> newVariantMR = new ArrayList<VariantModelRequest>(); // include the new variants (have
																						// id == null)
		Integer position = variantRepository.getMaxPositionOfVariant(idPet);
		System.out.println("position: " + position);
		for (VariantModelRequest variantModelReq : variantModelReqs) {
			if (variantModelReq.getId() == null) {
				newVariantMR.add(variantModelReq);
				continue;
			}
			Variant variant = variantRepository.findByIdAndStatus(variantModelReq.getId(), idPet, true);
			if (variant == null) {
				throw new EntityNotFoundException(variantModelReq.getId(), "Variant");
			}
			if (getVariantFromVariantModelReqUpdate(idPet, variant, variantModelReq, date))
				continue;

		}
		if (newVariantMR.size() != 0)
			saveVariant(idPet, newVariantMR, date, ++position);
	}

	private boolean getVariantFromVariantModelReqUpdate(Integer idPet, Variant variant,
			VariantModelRequest variantModelReq, Date date) {

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
			return true;
		} else
			saveOptionToUpdate(idPet, variant, variantModelReq, date);
		return false;
	}

	public void saveOptionToUpdate(Integer idPet, Variant variant, VariantModelRequest variantModelReq, Date date) {
		String[] nameOptions = new String[3];
		String[] values = new String[3];
		int i = 0;
		for (OptionModelRequest oMR : variantModelReq.getOptions()) {
			nameOptions[i] = oMR.getName();
			values[i] = oMR.getValue();
			i++;
		}
		

		variant.setModifiedOn(date);
		variantRepository.save(variant);
		List<Option> currentOptions = variantRepository.findAllOptionByIdVariant(variant.getId(), true);
		i = 0;
		while (nameOptions[i] != null && values[i] != null) {
			currentOptions.get(i).setName(nameOptions[i]);
			currentOptions.get(i).setModifiedOn(date);
			currentOptions.get(i).setValue(values[i]);
			optionRepository.save(currentOptions.get(i));
			i++;
			if (currentOptions.size() == i)
				break;
		}
	}

	private void saveVariant(Integer idPet, List<VariantModelRequest> variantModelReqs, Date date, Integer position) {
		for (VariantModelRequest variantModelReq : variantModelReqs) {
			Variant variant = new Variant();
			saveVariantSingle(variant, idPet, variantModelReq, position, date);
			position++;
			if (variantModelReq.getOptions() == null)
				saveOptionDefaultWhenOptionRequestNull(variant, idPet, date);

			else
				saveOptionWhenOptionRequestNotNull(variant, idPet, variantModelReq, date);
		}
	}

	private void saveVariantSingle(Variant variant, Integer idPet, VariantModelRequest variantModelReq, Integer position, Date date) {
		variant.setVariant(variantModelReq, idPet, position, date);
		variantRepository.save(variant);
	}

	private void saveOptionDefaultWhenOptionRequestNull(Variant variant, Integer idPet, Date date) {
		Option option = new Option();
		option.setOptionDefault(option, date, variant.getId()); // set default option
		optionRepository.save(option);
	}

	private void saveOptionWhenOptionRequestNotNull(Variant variant, Integer idPet, VariantModelRequest variantModelReq,
			Date date) {
		String[] nameOptions = new String[Variant.MAX_OPTION];
		String[] values = new String[Variant.MAX_OPTION];
		getNameAndValueOption(nameOptions, values, variantModelReq);
		saveOption(nameOptions, values, variant.getId(), date);
	}

	private void getNameAndValueOption(String[] nameOptions, String[] values, VariantModelRequest variantModelReq) {
		int i = 0;
		for (OptionModelRequest oMR : variantModelReq.getOptions()) {
			nameOptions[i] = oMR.getName();
			values[i] = oMR.getValue();
			if (i > 0) {
				if (nameOptions[i - 1].compareToIgnoreCase(nameOptions[i]) == 0
						|| values[i - 1].compareToIgnoreCase(values[i]) == 0) // nếu bị lặp option trong cùng một variant
																				// 
					throw new IteratedOptionException();
			}
			i++;
		}
	}

	public void saveOption(String[] nameOptions, String[] values, Integer idVariant, Date date) {
		for (int i = 0; i < nameOptions.length; i++) {
			Option option = new Option();
			option.setOption(option, nameOptions[i], values[i], i + 1, date, idVariant);
			optionRepository.save(option);
		}
	}

	private void saveDefaultEntities(Integer idPet, Date date) {
		Variant variant = new Variant();
		Option option = new Option();
		variant.setVariantDefault(variant, idPet, date, date); // set default variant
		variantRepository.save(variant);

		option.setOptionDefault(option, date, variant.getId()); // set default option
		optionRepository.save(option);
		
	}

}
