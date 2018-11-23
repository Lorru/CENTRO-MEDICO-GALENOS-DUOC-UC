package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.medicalcenter.galenos.api.models.entity.Specialist;

public interface ISpecialistDao extends JpaRepository<Specialist, Long> {
	
	@Query("SELECT s FROM Specialist s WHERE s.specialistStatus = 1")
	public List<Specialist> findAllSpecialistCount();
	
	@Query("SELECT s FROM Specialist s WHERE s.specialistStatus = 1")
	public List<Specialist> findAllSpecialist(Pageable pageable);
	
	@Query("SELECT s FROM Specialist s WHERE s.specialtyId.specialtyId = :specialtyId AND s.branchOfficeId.branchOfficeId = :branchOfficeId AND s.specialistStatus = 1")
	public List<Specialist> findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(@Param("specialtyId") long specialtyId, @Param("branchOfficeId") long branchOfficeId);
	
	@Modifying
	@Query("UPDATE Specialist s SET s.specialistStatus = :specialistStatus WHERE s.specialistId = :specialistId")
	public int updateSpecialistBySpecialistIdAndSpecialistStatus(@Param("specialistId") long specialistId, @Param("specialistStatus") char specialistStatus);
	
	@Query("SELECT s FROM Specialist s WHERE s.specialistRun = :specialistRun")
	public Specialist findByRun(@Param("specialistRun") String specialistRun);
	
	@Query("SELECT s FROM Specialist s WHERE s.specialistEmail = :specialistEmail")
	public Specialist findByEmail(@Param("specialistEmail") String specialistEmail);
	
	@Modifying
	@Query("UPDATE Specialist s SET s.specialistRun = :specialistRun , s.specialistFirstName = :specialistFirstName , s.specialistSecondName = :specialistSecondName, s.specialistSurName = :specialistSurName, s.specialistSecondSurName = :specialistSecondSurName, s.specialistFullName = :specialistFullName, s.specialistBirthDate = :specialistBirthDate, s.specialistEmail = :specialistEmail, s.genderId.genderId = :genderId, s.specialtyId.specialtyId = :specialtyId, s.branchOfficeId.branchOfficeId = :branchOfficeId  WHERE s.specialistId = :specialistId")
	public int updateSpecialistBySpecialistId(@Param("specialistRun") String specialistRun, @Param("specialistFirstName") String specialistFirstName, @Param("specialistSecondName") String specialistSecondName, @Param("specialistSurName") String specialistSurName, @Param("specialistSecondSurName") String specialistSecondSurName, @Param("specialistFullName") String specialistFullName, @Param("specialistBirthDate") LocalDate specialistBirthDate, @Param("specialistEmail") String specialistEmail, @Param("genderId") long genderId, @Param("specialtyId") long specialtyId, @Param("branchOfficeId") long branchOfficeId, @Param("specialistId") long specialistId);
}
