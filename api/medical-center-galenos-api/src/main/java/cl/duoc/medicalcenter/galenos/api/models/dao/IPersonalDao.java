package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.medicalcenter.galenos.api.models.entity.Personal;

public interface IPersonalDao extends JpaRepository<Personal, Long> {

	@Query("SELECT p FROM Personal p WHERE p.personalStatus = 1")
	public List<Personal> findAllPersonalByStatusActiveCount();
	
	@Query("SELECT p FROM Personal p WHERE p.personalStatus = 1")
	public List<Personal> findAllPersonalByStatusActive(Pageable pageable);
	
	@Modifying
	@Query("UPDATE Personal p SET p.personalRun = :personalRun, p.personalFirstName = :personalFirstName, p.personalSecondName = :personalSecondName, p.personalSurName = :personalSurName, p.personalSecondSurName = :personalSecondSurName, p.personalFullName = :personalFullName, p.personalBirthDate = :personalBirthDate, p.personalEmail = :personalEmail, p.genderId.genderId = :genderId, p.categoryId.categoryId = :categoryId, p.branchOfficeId.branchOfficeId = :branchOfficeId WHERE p.personalId = :personalId")
	public int updatePersonalByPersonalId(@Param("personalRun") String personalRun, @Param("personalFirstName") String personalFirstName, @Param("personalSecondName") String personalSecondName, @Param("personalSurName") String personalSurName, @Param("personalSecondSurName") String personalSecondSurName, @Param("personalFullName") String personalFullName, @Param("personalBirthDate") LocalDate personalBirthDate, @Param("personalEmail") String personalEmail, @Param("genderId") long genderId, @Param("categoryId") long categoryId, @Param("branchOfficeId") long branchOfficeId, @Param("personalId") long personalId);
	
	@Query("SELECT p FROM Personal p WHERE p.personalEmail = :personalEmail")
	public Personal findByEmail(@Param("personalEmail") String personalEmail);
	
	@Query("SELECT p FROM Personal p WHERE p.personalRun = :personalRun")
	public Personal findByRun(@Param("personalRun") String personalRun);
	
	@Modifying
	@Query("UPDATE Personal p SET p.personalStatus = :personalStatus WHERE p.personalId = :personalId")
	public int updatePersonalByPersonalIdAndPersonalStatus(@Param("personalId") long personalId, @Param("personalStatus") char personalStatus);
}
