package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.medicalcenter.galenos.api.models.entity.Patient;

public interface IPatientDao extends JpaRepository<Patient, Long> {

	@Query("SELECT p FROM Patient p WHERE p.patientRun = :patientRun")
	public Patient findByRun(@Param("patientRun") String patientRun);
	
	@Query("SELECT p FROM Patient p WHERE p.patientStatus = 1")
	public List<Patient> findAllPatientByStatusActive();
	
	@Query("SELECT p FROM Patient p WHERE p.patientEmail = :patientEmail")
	public Patient findByEmail(@Param("patientEmail") String patientEmail);
	
	@Modifying
	@Query("UPDATE Patient p SET p.patientRun = :patientRun , p.patientFirstName = :patientFirstName , p.patientSecondName = :patientSecondName, p.patientSurName = :patientSurName, p.patientSecondSurName = :patientSecondSurName, p.patientFullName = :patientFullName, p.patientBirthDate = :patientBirthDate, p.patientEmail = :patientEmail, p.genderId.genderId = :genderId WHERE p.patientId = :patientId")
	public int updatePatientByPatientId(@Param("patientRun") String patientRun, @Param("patientFirstName") String patientFirstName, @Param("patientSecondName") String patientSecondName, @Param("patientSurName") String patientSurName, @Param("patientSecondSurName") String patientSecondSurName, @Param("patientFullName") String patientFullName, @Param("patientBirthDate") LocalDate patientBirthDate, @Param("patientEmail") String patientEmail, @Param("genderId") long genderId, @Param("patientId") long patientId);
	
	@Modifying
	@Query("UPDATE Patient p SET p.patientStatus = :patientStatus WHERE p.patientId = :patientId")
	public int updatePatientByPatientIdAndPatientStatus(@Param("patientId") long patientId, @Param("patientStatus") char patientStatus);
}
