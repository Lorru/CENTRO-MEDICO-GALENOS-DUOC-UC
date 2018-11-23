package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.Role;

public interface IRoleService {

	public List<Role> findAll();
}
