package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.duoc.medicalcenter.galenos.api.models.entity.EventLog;

public interface IEventLogDao extends JpaRepository<EventLog, Long> {
	
	@Query("SELECT e FROM EventLog e")
	public List<EventLog> findAllEventLogCount();
	
	@Query("SELECT e FROM EventLog e")
	public List<EventLog> findAllEventLog(Pageable pageable);
}
