package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IProfileDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Profile;

@Service
public class ProfileServiceImpl implements IProfileService {

	@Autowired
	private IProfileDao iProfileDao;
	
	@Override
	@Transactional
	public List<Profile> findAll() {
		
		return iProfileDao.findAll();
	}

	@Override
	@Transactional
	public List<Profile> findAllProfileByStatusActive() {
		return iProfileDao.findAllProfileByStatusActive();
	}

	@Override
	@Transactional
	public Profile findById(long patientId) {
		return iProfileDao.findById(patientId).orElse(null);
	}
}
