package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.StateMedicalTime;

public interface IStateMedicalTimeService {

	public List<StateMedicalTime> findAll();
}
