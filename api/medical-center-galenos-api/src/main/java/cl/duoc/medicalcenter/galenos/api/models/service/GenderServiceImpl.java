package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IGenderDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Gender;

@Service
public class GenderServiceImpl implements IGenderService {

	@Autowired
	private IGenderDao iGenderDao;
	
	@Override
	@Transactional
	public List<Gender> findAll() {
		
		return iGenderDao.findAll();
	}

	@Override
	@Transactional
	public List<Gender> findAllGenderByStatusActive() {
		return iGenderDao.findAllGenderByStatusActive();
	}
}
