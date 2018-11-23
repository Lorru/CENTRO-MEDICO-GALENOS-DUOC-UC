package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.medicalcenter.galenos.api.models.entity.ProfileRole;

public interface IProfileRoleDao extends JpaRepository<ProfileRole, Long> {

	@Query("SELECT pr.roleId FROM ProfileRole pr WHERE pr.profileId.profileId = :profileId")
	public List<ProfileRole> findAllByProfileId(@Param("profileId") long profileId);
}
