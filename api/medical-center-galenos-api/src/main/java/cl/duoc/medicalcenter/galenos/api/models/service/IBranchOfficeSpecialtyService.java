package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOfficeSpecialty;

public interface IBranchOfficeSpecialtyService {

	public List<BranchOfficeSpecialty> findAll();
}
