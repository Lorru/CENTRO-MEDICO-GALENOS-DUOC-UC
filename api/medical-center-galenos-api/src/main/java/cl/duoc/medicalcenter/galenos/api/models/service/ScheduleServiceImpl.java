package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IScheduleDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Schedule;

@Service
public class ScheduleServiceImpl implements IScheduleService {

	@Autowired
	private IScheduleDao iScheduleDao;
	
	@Override
	@Transactional
	public List<Schedule> findAllScheduleBySpecialistIdAndByBillMedicalTimeAndStatusActive(long specialistId, String billMedicalTime) {
		return iScheduleDao.findAllScheduleBySpecialistIdAndByBillMedicalTimeAndStatusActive(specialistId, billMedicalTime);
	}
}
