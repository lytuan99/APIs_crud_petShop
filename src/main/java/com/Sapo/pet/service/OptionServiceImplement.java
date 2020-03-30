package com.Sapo.pet.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Sapo.pet.entities.Option;
import com.Sapo.pet.repository.OptionRepository;
import com.Sapo.pet.service.serviceInterface.OptionService;

@Service
public class OptionServiceImplement implements OptionService {

	@Autowired private OptionRepository optionRepository;
	@Override
	public Optional<Option> getId(Integer id) {
		return optionRepository.findById(id);
	}

	
}
