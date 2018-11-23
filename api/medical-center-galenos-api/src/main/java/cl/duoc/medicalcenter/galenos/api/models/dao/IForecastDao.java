package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.duoc.medicalcenter.galenos.api.models.entity.Forecast;

public interface IForecastDao extends JpaRepository<Forecast, Long> {

	@Query("SELECT f FROM Forecast f WHERE f.forecastStatus = 1")
	public List<Forecast> findAllForecastByStatusActive();
}
