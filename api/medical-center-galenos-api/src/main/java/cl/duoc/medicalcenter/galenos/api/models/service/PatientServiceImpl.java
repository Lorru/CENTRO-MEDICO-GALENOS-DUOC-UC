package cl.duoc.medicalcenter.galenos.api.models.service;

import java.time.LocalDate;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IPatientDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Patient;

@Service
public class PatientServiceImpl implements IPatientService {

	@Autowired
	private IPatientDao iPatientDao;
	
	@Override
	@Transactional
	public Patient save(Patient patient) {
		
		return iPatientDao.save(patient);
	}

	@Override
	@Transactional
	public Patient findByRun(String patientRun) {
		return iPatientDao.findByRun(patientRun);
	}
	
	@Override
	@Transactional
	public Patient findByEmail(String patientEmail) {
		return iPatientDao.findByEmail(patientEmail);
	}

	@Override
	@Transactional
	public List<Patient> findAllPatientByStatusActive() {
		return iPatientDao.findAllPatientByStatusActive();
	}

	@Override
	@Transactional
	public int updatePatientByPatientId(String patientRun, String patientFirstName, String patientSecondName, String patientSurName, String patientSecondSurName, String patientFullName, LocalDate patientBirthDate, String patientEmail, long genderId, long patientId) {
		return iPatientDao.updatePatientByPatientId(patientRun, patientFirstName, patientSecondName, patientSurName, patientSecondSurName, patientFullName, patientBirthDate, patientEmail, genderId, patientId);
	}

	@Override
	@Transactional
	public int updatePatientByPatientIdAndPatientStatus(long patientId, char patientStatus) {
		return iPatientDao.updatePatientByPatientIdAndPatientStatus(patientId, patientStatus);
	}

}
