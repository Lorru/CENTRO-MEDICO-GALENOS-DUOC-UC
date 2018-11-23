package cl.duoc.medicalcenter.galenos.api.models.service;

import java.time.LocalDate;
import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.Patient;

public interface IPatientService {
	
	public Patient save(Patient patient);
	
	public Patient findByRun(String patientRun);
	
	public Patient findByEmail(String patientEmail);
	
	public List<Patient> findAllPatientByStatusActive();
	
	public int updatePatientByPatientId(String patientRun, String patientFirstName, String patientSecondName, String patientSurName, String patientSecondSurName, String patientFullName, LocalDate patientBirthDate, String patientEmail, long genderId, long patientId);
	
	public int updatePatientByPatientIdAndPatientStatus(long patientId, char patientStatus);
}
