package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.ProfileRole;

public interface IProfileRoleService {

	public List<ProfileRole> findAllByProfileId(long profileId);
}
