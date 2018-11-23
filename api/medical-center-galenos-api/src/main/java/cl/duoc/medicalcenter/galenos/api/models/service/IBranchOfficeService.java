package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOffice;

public interface IBranchOfficeService {
	
	public List<BranchOffice> findAllBranchOfficeByStatusActive();
	
}
