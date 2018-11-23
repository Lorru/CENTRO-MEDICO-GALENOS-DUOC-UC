package cl.duoc.medicalcenter.galenos.api.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import cl.duoc.medicalcenter.galenos.api.models.entity.Specialist;

public interface ISpecialistService {

	public List<Specialist> findAllSpecialist(Pageable pageable);
	
	public List<Specialist> findAllSpecialistCount();
	
	public List<Specialist> findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(long specialtyId, long branchOfficeId);
	
	public int updateSpecialistBySpecialistIdAndSpecialistStatus(long specialistId, char specialistStatus);
	
	public Specialist findByRun(String specialistRun);
	
	public Specialist findByEmail(String specialistEmail);
	
	public Specialist save(Specialist specialist);
	
	public int updateSpecialistBySpecialistId(String specialistRun, String specialistFirstName, String specialistSecondName, String specialistSurName, String specialistSecondSurName, String specialistFullName, LocalDate specialistBirthDate, String specialistEmail, long genderId, long specialtyId, long branchOfficeId, long specialistId);
}
