package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.medicalcenter.galenos.api.models.entity.Specialty;

public interface ISpecialtyDao extends JpaRepository<Specialty, Long> {

	@Query("SELECT bs.specialtyId FROM BranchOfficeSpecialty bs WHERE bs.branchOfficeId.branchOfficeId = :branchOfficeId AND bs.specialtyId.specialtyStatus = 1")
	public List<Specialty> findAllSpecialtyByBranchOfficeIdAndByStatusActive(@PathVariable("branchOfficeId") long branchOfficeId);
	
	@Query("SELECT s FROM Specialty s WHERE s.specialtyStatus = 1")
	public List<Specialty> findAllSpecialtytByStatusActive();
}
