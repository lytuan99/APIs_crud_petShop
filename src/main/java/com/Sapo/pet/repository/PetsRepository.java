package com.Sapo.pet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Sapo.pet.entities.Pet;
import com.Sapo.pet.entities.Variant;

@Repository
public interface PetsRepository extends JpaRepository<Pet, Integer> {

	@Query(value = "SELECT p FROM Pet p")
	List<Pet> findAllPets();
	
	@Query(value = "SELECT p FROM Pet p WHERE p.id = :id and p.status = :status")
	Pet findByIdAndStatus(@Param("id") Integer id,@Param("status") Boolean status);
	

	
	@Query(value = "SELECT v FROM Pet p, Variant v WHERE p.id = :id and p.id = v.idPet and p.status = :status")
	List<Variant> findAllVariantsByIdPetAndStatus(@Param("id") Integer id,@Param("status") Boolean status);
}
