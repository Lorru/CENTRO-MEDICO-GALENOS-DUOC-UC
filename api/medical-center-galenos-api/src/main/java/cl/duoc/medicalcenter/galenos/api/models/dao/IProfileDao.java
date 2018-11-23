package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.duoc.medicalcenter.galenos.api.models.entity.Profile;

public interface IProfileDao extends JpaRepository<Profile, Long> {

	@Query("SELECT p FROM Profile p WHERE p.profileStatus = 1")
	public List<Profile> findAllProfileByStatusActive();
}
