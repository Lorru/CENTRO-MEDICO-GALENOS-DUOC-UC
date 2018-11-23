package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IForecastDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Forecast;

@Service
public class ForecastServiceImpl implements IForecastService {

	@Autowired
	private IForecastDao iForecastDao;
	
	@Override
	@Transactional
	public List<Forecast> findAllForecastByStatusActive() {
		return iForecastDao.findAllForecastByStatusActive();
	}
}
