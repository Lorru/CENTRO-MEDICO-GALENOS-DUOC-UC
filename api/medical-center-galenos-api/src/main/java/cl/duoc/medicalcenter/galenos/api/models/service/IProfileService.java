package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.Profile;

public interface IProfileService {

	public List<Profile> findAll();
	
	public List<Profile> findAllProfileByStatusActive();
	
	public Profile findById(long patientId);
}
