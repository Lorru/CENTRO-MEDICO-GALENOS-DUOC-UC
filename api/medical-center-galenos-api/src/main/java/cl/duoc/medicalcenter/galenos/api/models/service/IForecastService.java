package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.Forecast;

public interface IForecastService {
	
	public List<Forecast> findAllForecastByStatusActive();
}
