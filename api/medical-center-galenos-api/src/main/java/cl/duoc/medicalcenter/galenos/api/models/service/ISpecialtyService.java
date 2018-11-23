package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.Specialty;

public interface ISpecialtyService {

	public List<Specialty> findAllSpecialtyByBranchOfficeIdAndByStatusActive(long branchOfficeId);
	
	public List<Specialty> findAllSpecialtytByStatusActive();
}
