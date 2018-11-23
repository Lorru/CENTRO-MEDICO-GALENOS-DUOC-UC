package cl.duoc.medicalcenter.galenos.api.models.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IPersonalDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Personal;

@Service
public class PersonalServiceImpl implements IPersonalService {

	@Autowired
	private IPersonalDao iPersonalDao;
	

	@Override
	@Transactional
	public List<Personal> findAllPersonalByStatusActiveCount() {
		return iPersonalDao.findAllPersonalByStatusActiveCount();
	}

	@Override
	@Transactional
	public List<Personal> findAllPersonalByStatusActive(Pageable pageable) {
		return iPersonalDao.findAllPersonalByStatusActive(pageable);
	}

	@Override
	@Transactional
	public Personal save(Personal personal) {
		return iPersonalDao.save(personal);
	}

	@Override
	@Transactional
	public int updatePersonalByPersonalId(String personalRun, String personalFirstName, String personalSecondName, String personalSurName, String personalSecondSurName, String personalFullName, LocalDate personalBirthDate, String personalEmail, long genderId, long categoryId, long branchOfficeId, long personalId) {
		return iPersonalDao.updatePersonalByPersonalId(personalRun, personalFirstName, personalSecondName, personalSurName, personalSecondSurName, personalFullName, personalBirthDate, personalEmail, genderId, categoryId, branchOfficeId, personalId);
	}

	@Override
	@Transactional
	public Personal findByEmail(String personalEmail) {
		return iPersonalDao.findByEmail(personalEmail);
	}

	@Override
	@Transactional
	public Personal findByRun(String personalRun) {
		return iPersonalDao.findByRun(personalRun);
	}

	@Override
	@Transactional
	public int updatePersonalByPersonalIdAndPersonalStatus(long personalId, char personalStatus) {
		return iPersonalDao.updatePersonalByPersonalIdAndPersonalStatus(personalId, personalStatus);
	}

}
