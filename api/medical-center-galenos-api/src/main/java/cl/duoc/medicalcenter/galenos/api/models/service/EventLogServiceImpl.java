package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IEventLogDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.EventLog;

@Service
public class EventLogServiceImpl implements IEventLogService {

	@Autowired
	private IEventLogDao iEventLogDao;

	@Override
	@Transactional
	public EventLog save(EventLog eventLog) {
		return iEventLogDao.save(eventLog);
	}

	@Override
	@Transactional
	public List<EventLog> findAllEventLogCount() {
		return iEventLogDao.findAllEventLogCount();
	}

	@Override
	@Transactional
	public List<EventLog> findAllEventLog(Pageable pageable) {
		return iEventLogDao.findAllEventLog(pageable);
	}

}
