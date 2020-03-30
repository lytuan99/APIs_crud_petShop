package com.Sapo.pet.service.serviceInterface;

import java.util.Optional;

import com.Sapo.pet.entities.Option;

public interface OptionService {
	
	Optional<Option> getId(Integer id);
	
}
