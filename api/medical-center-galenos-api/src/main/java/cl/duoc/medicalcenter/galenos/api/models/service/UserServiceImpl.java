package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IUserDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.User;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao iUserDao;
	
	@Override
	@Transactional
	public User save(User user) {
		return iUserDao.save(user);
	}

	@Override
	@Transactional
	public User login(String emailPatient, String emailSpecialist, String emailPersonal) {
		return iUserDao.login(emailPatient, emailSpecialist, emailPersonal);
	}


	@Override
	@Transactional
	public User findByUserIdAndStatusActive(long userId) {
		return iUserDao.findByUserIdAndStatusActive(userId);
	}

	@Override
	@Transactional
	public int updateUserByUserId(String userPassword, long userId) {
		return iUserDao.updateUserByUserId(userPassword, userId);
	}

	@Override
	@Transactional
	public List<User> findAllUserCount() {
		return iUserDao.findAllUserCount();
	}

	@Override
	@Transactional
	public List<User> findAllUser(Pageable pageable) {
		return iUserDao.findAllUser(pageable);
	}

	@Override
	@Transactional
	public int updateUserByUserIdAndUserStatus(long userId, char userStatus) {
		return iUserDao.updateUserByUserIdAndUserStatus(userId, userStatus);
	}

	@Override
	@Transactional
	public User findByUserId(long userId) {
		return iUserDao.findByUserId(userId);
	}

	@Override
	@Transactional
	public User findUserByPatientId(long patientId) {
		return iUserDao.findUserByPatientId(patientId);
	}

}
