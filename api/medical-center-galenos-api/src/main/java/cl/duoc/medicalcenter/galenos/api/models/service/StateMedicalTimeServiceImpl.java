package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IStateMedicalTimeDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.StateMedicalTime;

@Service
public class StateMedicalTimeServiceImpl implements IStateMedicalTimeService {

	
	@Autowired
	private IStateMedicalTimeDao iStateMedicalTime;
	
	@Override
	@Transactional
	public List<StateMedicalTime> findAll() {
		
		return iStateMedicalTime.findAll();
	}

}
