package com.Sapo.pet;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PetApplication implements CommandLineRunner{

	@Override
	public void run(String ...args) throws Exception{
		System.out.println("___________L Y T U A N____________");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(PetApplication.class, args);
	}

}
