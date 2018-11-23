package cl.duoc.medicalcenter.galenos.api.models.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOfficeSpecialty;

public interface IBranchOfficeSpecialtyDao extends JpaRepository<BranchOfficeSpecialty, Long> {

}
