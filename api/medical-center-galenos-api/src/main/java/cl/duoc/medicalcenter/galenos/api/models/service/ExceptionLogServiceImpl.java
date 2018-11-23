package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IExceptionLogDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;

@Service
public class ExceptionLogServiceImpl implements IExceptionLogService {

	@Autowired
	private IExceptionLogDao iExceptionLogDao;

	@Override
	@Transactional
	public ExceptionLog save(ExceptionLog exceptionLog) {
		return iExceptionLogDao.save(exceptionLog);
	}

	@Override
	@Transactional
	public List<ExceptionLog> findAllExceptionLogCount() {
		return iExceptionLogDao.findAllExceptionLogCount();
	}

	@Override
	@Transactional
	public List<ExceptionLog> findAllExceptionLog(Pageable pageable) {
		return iExceptionLogDao.findAllExceptionLog(pageable);
	}

}
