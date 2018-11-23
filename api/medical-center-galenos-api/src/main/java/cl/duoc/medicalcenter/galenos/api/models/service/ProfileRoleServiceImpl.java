package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IProfileRoleDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.ProfileRole;

@Service
public class ProfileRoleServiceImpl implements IProfileRoleService {

	@Autowired
	private IProfileRoleDao iProfileRoleDao;
	
	@Override
	@Transactional
	public List<ProfileRole> findAllByProfileId(long profileId) {
		return iProfileRoleDao.findAllByProfileId(profileId);
	}

}
