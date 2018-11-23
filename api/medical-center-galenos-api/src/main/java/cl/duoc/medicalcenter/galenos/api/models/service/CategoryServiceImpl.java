package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.ICategoryDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Category;

@Service
public class CategoryServiceImpl implements ICategoryService {

	@Autowired
	private ICategoryDao iCategoryDao;
	
	@Override
	@Transactional
	public List<Category> findAllCategoryByBranchOfficeIdAndByStatusActive(long branchOfficeId) {
		return iCategoryDao.findAllCategoryByBranchOfficeIdAndByStatusActive(branchOfficeId);
	}

}
