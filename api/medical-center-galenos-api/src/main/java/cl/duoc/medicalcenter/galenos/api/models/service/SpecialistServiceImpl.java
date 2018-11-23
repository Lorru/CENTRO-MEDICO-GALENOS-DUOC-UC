package cl.duoc.medicalcenter.galenos.api.models.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.ISpecialistDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Specialist;

@Service
public class SpecialistServiceImpl implements ISpecialistService {

	@Autowired
	private ISpecialistDao iSpecialistDao;
	
	@Override
	@Transactional
	public List<Specialist> findAllSpecialist(Pageable pageable) {
		return iSpecialistDao.findAllSpecialist(pageable);
	}

	@Override
	@Transactional
	public List<Specialist> findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(long specialtyId, long branchOfficeId) {
		return iSpecialistDao.findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(specialtyId, branchOfficeId);
	}

	@Override
	@Transactional
	public int updateSpecialistBySpecialistIdAndSpecialistStatus(long specialistId, char specialistStatus) {
		return iSpecialistDao.updateSpecialistBySpecialistIdAndSpecialistStatus(specialistId, specialistStatus);
	}

	@Override
	@Transactional
	public Specialist findByRun(String specialistRun) {
		return iSpecialistDao.findByRun(specialistRun);
	}

	@Override
	@Transactional
	public Specialist findByEmail(String specialistEmail) {
		return iSpecialistDao.findByEmail(specialistEmail);
	}

	@Override
	@Transactional
	public Specialist save(Specialist specialist) {
		return iSpecialistDao.save(specialist);
	}

	@Override
	@Transactional
	public List<Specialist> findAllSpecialistCount() {
		return iSpecialistDao.findAllSpecialistCount();
	}

	@Override
	@Transactional
	public int updateSpecialistBySpecialistId(String specialistRun, String specialistFirstName, String specialistSecondName, String specialistSurName, String specialistSecondSurName, String specialistFullName, LocalDate specialistBirthDate, String specialistEmail, long genderId, long specialtyId, long branchOfficeId, long specialistId) {
		return iSpecialistDao.updateSpecialistBySpecialistId(specialistRun, specialistFirstName, specialistSecondName, specialistSurName, specialistSecondSurName, specialistFullName, specialistBirthDate, specialistEmail, genderId, specialtyId, branchOfficeId, specialistId);
	}

}
