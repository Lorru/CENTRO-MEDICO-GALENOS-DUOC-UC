package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import cl.duoc.medicalcenter.galenos.api.models.entity.Category;

public interface ICategoryDao extends JpaRepository<Category, Long> {

	@Query("SELECT bc.categoryId FROM BranchOfficeCategory bc WHERE bc.branchOfficeId.branchOfficeId = :branchOfficeId AND bc.categoryId.categoryStatus = 1")
	public List<Category> findAllCategoryByBranchOfficeIdAndByStatusActive(@PathVariable("branchOfficeId") long branchOfficeId);
}
