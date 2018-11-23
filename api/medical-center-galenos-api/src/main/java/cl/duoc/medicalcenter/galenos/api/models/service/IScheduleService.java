package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.Schedule;

public interface IScheduleService {
	
	public List<Schedule> findAllScheduleBySpecialistIdAndByBillMedicalTimeAndStatusActive(long specialistId, String billMedicalTime);
}
