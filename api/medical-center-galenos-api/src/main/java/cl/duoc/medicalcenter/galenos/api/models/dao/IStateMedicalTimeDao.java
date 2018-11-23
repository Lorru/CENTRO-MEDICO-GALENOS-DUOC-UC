package cl.duoc.medicalcenter.galenos.api.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.medicalcenter.galenos.api.models.entity.StateMedicalTime;

public interface IStateMedicalTimeDao extends JpaRepository<StateMedicalTime, Long> {

}
