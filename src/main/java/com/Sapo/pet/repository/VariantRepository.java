package com.Sapo.pet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Sapo.pet.entities.Option;
import com.Sapo.pet.entities.Variant;

@Repository
public interface VariantRepository extends JpaRepository<Variant, Integer>{

	@Query(value = "SELECT v FROM Variant v")
	List<Variant> findAllVariants();
	
	@Query(value = "SELECT v FROM Variant v WHERE v.id = :id and v.status = :status")
	Variant findByIdAndStatus(@Param("id") Integer id,@Param("status") Boolean status);
	
	@Query(value = "SELECT v FROM Variant v WHERE v.id = :id and v.idPet = :id_pet and v.status = :status")
	Variant findByIdAndStatus(@Param("id") Integer id, @Param("id_pet") Integer idPet, @Param("status") Boolean status);
	
	@Query(value = "SELECT o FROM Variant v, Option o WHERE v.id = :id and o.idVariant = v.id and v.status = :status")
	List<Option> findAllOptionByIdVariant(@Param("id") Integer id,@Param("status") Boolean status);
	
	@Query(value = "SELECT COUNT(v.idPet) FROM Variant v WHERE v.idPet = :id")
	Integer countVariantByIdPet(@Param("id") Integer id);

	@Query(value = "SELECT MAX(v.position) FROM Variant v, Pet p WHERE v.idPet = :idPet and p.id = :idPet")
	Integer getMaxPositionOfVariant(@Param("idPet") Integer idPet);
	
}
