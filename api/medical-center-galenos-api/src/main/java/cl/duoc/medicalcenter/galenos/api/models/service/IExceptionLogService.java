package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;

public interface IExceptionLogService {
	
	public ExceptionLog save(ExceptionLog exceptionLog);
	
	public List<ExceptionLog> findAllExceptionLogCount();
	
	public List<ExceptionLog> findAllExceptionLog(Pageable pageable);
}
