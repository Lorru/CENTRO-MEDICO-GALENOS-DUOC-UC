package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.duoc.medicalcenter.galenos.api.models.entity.Gender;

public interface IGenderDao extends JpaRepository<Gender, Long>  {

	@Query("SELECT g FROM Gender g WHERE g.genderStatus = 1")
	public List<Gender> findAllGenderByStatusActive();
}
