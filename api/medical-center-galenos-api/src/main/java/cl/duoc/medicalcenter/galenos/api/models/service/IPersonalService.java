package cl.duoc.medicalcenter.galenos.api.models.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;

import cl.duoc.medicalcenter.galenos.api.models.entity.Personal;

public interface IPersonalService {

	public List<Personal> findAllPersonalByStatusActiveCount();
	
	public List<Personal> findAllPersonalByStatusActive(Pageable pageable);
	
	public Personal save(Personal personal);
	
	public int updatePersonalByPersonalId(String personalRun, String personalFirstName, String personalSecondName, String personalSurName, String personalSecondSurName, String personalFullName, LocalDate personalBirthDate, String personalEmail, long genderId, long categoryId, long branchOfficeId, long personalId);
	
	public Personal findByEmail(String personalEmail);
	
	public Personal findByRun(String personalRun);
	
	public int updatePersonalByPersonalIdAndPersonalStatus(long personalId, char personalStatus);
}
