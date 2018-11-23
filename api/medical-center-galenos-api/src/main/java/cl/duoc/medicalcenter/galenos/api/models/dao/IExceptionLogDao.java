package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;

public interface IExceptionLogDao extends JpaRepository<ExceptionLog, Long> {

	@Query("SELECT e FROM ExceptionLog e")
	public List<ExceptionLog> findAllExceptionLogCount();
	
	@Query("SELECT e FROM ExceptionLog e")
	public List<ExceptionLog> findAllExceptionLog(Pageable pageable);
	
}
