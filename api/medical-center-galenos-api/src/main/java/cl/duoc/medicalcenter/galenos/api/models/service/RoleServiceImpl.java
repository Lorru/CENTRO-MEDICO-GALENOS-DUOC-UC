package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IRoleDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Role;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao iRoleDao;
	
	@Override
	@Transactional
	public List<Role> findAll() {
		
		return iRoleDao.findAll();
	}
}
