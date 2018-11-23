package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cl.duoc.medicalcenter.galenos.api.models.entity.EventLog;

public interface IEventLogService {

	public List<EventLog> findAllEventLogCount();
	
	public List<EventLog> findAllEventLog(Pageable pageable);
	
	public EventLog save(EventLog eventLog);
}
