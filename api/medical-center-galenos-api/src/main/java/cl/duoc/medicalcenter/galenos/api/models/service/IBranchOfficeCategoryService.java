package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOfficeCategory;

public interface IBranchOfficeCategoryService {

	public List<BranchOfficeCategory> findAll();
}
