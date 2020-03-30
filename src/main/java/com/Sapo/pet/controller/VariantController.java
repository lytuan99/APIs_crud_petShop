package com.Sapo.pet.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Sapo.pet.service.serviceInterface.VariantService;

@RestController
@RequestMapping("/api")
public class VariantController {
	
	@Autowired private VariantService variantSevice;
	

}
