package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.ISpecialtyDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Specialty;

@Service
public class SpecialtyServiceImpl implements ISpecialtyService {

	@Autowired
	private ISpecialtyDao iSpecialtyDao;

	@Override
	@Transactional
	public List<Specialty> findAllSpecialtyByBranchOfficeIdAndByStatusActive(long branchOfficeId) {
		return iSpecialtyDao.findAllSpecialtyByBranchOfficeIdAndByStatusActive(branchOfficeId);
	}

	@Override
	@Transactional
	public List<Specialty> findAllSpecialtytByStatusActive() {
		return iSpecialtyDao.findAllSpecialtytByStatusActive();
	}
	

}
