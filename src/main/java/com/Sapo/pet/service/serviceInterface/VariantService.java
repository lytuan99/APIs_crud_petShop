package com.Sapo.pet.service.serviceInterface;

import com.Sapo.pet.entities.Variant;
import com.Sapo.pet.model.response.VariantModel;

public interface VariantService {
	
	public Variant getVariantById(Integer id);
	public boolean addVariant(VariantModel variantModel);
	public boolean editVariantById(Integer id, VariantModel variantModel);
	public boolean deleteVariant(Integer id);
}
