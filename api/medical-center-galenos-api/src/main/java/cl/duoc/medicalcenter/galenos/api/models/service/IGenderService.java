package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.Gender;

public interface IGenderService {

	public List<Gender> findAll();
	
	public List<Gender> findAllGenderByStatusActive();
}
