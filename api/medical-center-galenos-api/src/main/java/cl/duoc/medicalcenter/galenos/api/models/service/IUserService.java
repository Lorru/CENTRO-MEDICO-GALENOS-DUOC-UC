package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cl.duoc.medicalcenter.galenos.api.models.entity.User;

public interface IUserService {
	
	public User save(User user);
	
	public User login(String emailPatient, String emailSpecialist, String emailPersonal);
	
	public User findByUserIdAndStatusActive(long userId);
	
	public int updateUserByUserId(String userPassword, long userId);
	
	public List<User> findAllUserCount();
	
	public List<User> findAllUser(Pageable pageable);
	
	public int updateUserByUserIdAndUserStatus(long userId, char userStatus);
	
	public User findByUserId(long userId);
	
	public User findUserByPatientId(long patientId);
}
