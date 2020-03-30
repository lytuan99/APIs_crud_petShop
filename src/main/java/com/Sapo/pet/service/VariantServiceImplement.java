package com.Sapo.pet.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sapo.pet.entities.Variant;
import com.Sapo.pet.model.response.VariantModel;
import com.Sapo.pet.repository.VariantRepository;
import com.Sapo.pet.service.serviceInterface.VariantService;

@Service
public class VariantServiceImplement implements VariantService {

	@Autowired private VariantRepository variantRepository;

	@Override
	public Variant getVariantById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean addVariant(VariantModel variantModel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean editVariantById(Integer id, VariantModel variantModel) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteVariant(Integer id) {
		// TODO Auto-generated method stub
		return false;
	}

}
