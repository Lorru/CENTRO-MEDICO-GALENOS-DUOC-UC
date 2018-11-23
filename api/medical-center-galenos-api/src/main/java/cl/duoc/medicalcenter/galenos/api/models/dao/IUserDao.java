package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.medicalcenter.galenos.api.models.entity.User;

public interface IUserDao extends JpaRepository<User, Long> {

	@Query("SELECT u , p , s , pp FROM User u LEFT JOIN u.patientId p LEFT JOIN u.specialistId s LEFT JOIN u.personalId pp WHERE u.userStatus = '1' AND ( p.patientEmail = :emailPatient OR s.specialistEmail = :emailSpecialist OR pp.personalEmail = :emailPersonal )")
	public User login(@Param("emailPatient") String emailPatient, @Param("emailSpecialist") String emailSpecialist, @Param("emailPersonal") String emailPersonal);
	
	@Query("SELECT u , p , s , pp FROM User u LEFT JOIN u.patientId p LEFT JOIN u.specialistId s LEFT JOIN u.personalId pp WHERE u.userId = :userId AND u.userStatus = 1")
	public User findByUserIdAndStatusActive(@Param("userId") long userId);
	
	@Modifying
	@Query("UPDATE User u SET u.userPassword = :userPassword WHERE u.userId = :userId")
	public int updateUserByUserId(@Param("userPassword") String userPassword, @Param("userId") long userId);
	
	@Query("SELECT u FROM User u")
	public List<User> findAllUserCount();
	
	@Query("SELECT u FROM User u")
	public List<User> findAllUser(Pageable pageable);
	
	@Modifying
	@Query("UPDATE User u SET u.userStatus = :userStatus WHERE u.userId = :userId")
	public int updateUserByUserIdAndUserStatus(@Param("userId") long userId, @Param("userStatus") char userStatus);
	
	@Query("SELECT u , p , s , pp FROM User u LEFT JOIN u.patientId p LEFT JOIN u.specialistId s LEFT JOIN u.personalId pp WHERE u.userId = :userId")
	public User findByUserId(@Param("userId") long userId);
	
	@Query("SELECT u  FROM User u INNER JOIN u.patientId p WHERE u.userStatus = '1' AND u.patientId.patientId = :patientId ")
	public User findUserByPatientId(@Param("patientId") long patientId);
}
