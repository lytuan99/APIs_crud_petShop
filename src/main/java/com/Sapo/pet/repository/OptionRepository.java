package com.Sapo.pet.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Sapo.pet.entities.Option;

@Repository
public interface OptionRepository extends JpaRepository<Option, Integer>{

	@Query(value = "SELECT o FROM Option o")
	List<Option> findAllOptions();
	
	@Query(value = "SELECT v FROM Option v WHERE v.id = :id and v.status = :status")
	Option findByIdAndStatus(@Param("id") Integer id,@Param("status") Boolean status);
	
	@Query(value = "SELECT COUNT(o.idVariant) FROM Option o WHERE o.idVariant = :id")
	Integer countOptionByIdVariant(@Param("id") Integer id);
	
}
