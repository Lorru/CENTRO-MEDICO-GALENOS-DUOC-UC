package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IBranchOfficeCategoryDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOfficeCategory;

@Service
public class BranchOfficeCategoryServiceImpl implements IBranchOfficeCategoryService {

	@Autowired
	private IBranchOfficeCategoryDao iBranchOfficeCategoryDao;
	
	@Override
	@Transactional
	public List<BranchOfficeCategory> findAll() {
		return iBranchOfficeCategoryDao.findAll();
	}

}
