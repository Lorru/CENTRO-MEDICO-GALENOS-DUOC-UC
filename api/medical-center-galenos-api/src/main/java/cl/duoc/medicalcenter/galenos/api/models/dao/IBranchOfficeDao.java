package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOffice;

public interface IBranchOfficeDao extends JpaRepository<BranchOffice, Long> {

	@Query("SELECT b FROM BranchOffice b WHERE b.branchOfficeStatus = 1")
	public List<BranchOffice> findAllBranchOfficeByStatusActive();
}
