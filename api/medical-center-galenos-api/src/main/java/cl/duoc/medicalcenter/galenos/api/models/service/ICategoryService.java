package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.Category;

public interface ICategoryService {

	public List<Category> findAllCategoryByBranchOfficeIdAndByStatusActive(long branchOfficeId);
}
