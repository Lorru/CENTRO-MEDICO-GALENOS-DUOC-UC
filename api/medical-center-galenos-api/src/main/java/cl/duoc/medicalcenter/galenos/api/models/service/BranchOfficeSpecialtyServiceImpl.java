package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import cl.duoc.medicalcenter.galenos.api.models.dao.IBranchOfficeSpecialtyDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOfficeSpecialty;

public class BranchOfficeSpecialtyServiceImpl implements IBranchOfficeSpecialtyService {

	@Autowired
	private IBranchOfficeSpecialtyDao iBranchOfficeSpecialtyDao;
	
	@Override
	@Transactional
	public List<BranchOfficeSpecialty> findAll() {
		return iBranchOfficeSpecialtyDao.findAll();
	}

}
