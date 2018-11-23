package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.medicalcenter.galenos.api.models.entity.Schedule;

public interface IScheduleDao extends JpaRepository<Schedule, Long> {
	
	@Query("SELECT s FROM Schedule s WHERE NOT s.scheduleId IN (SELECT b.scheduleId.scheduleId FROM Bill b WHERE b.specialistId.specialistId = :specialistId AND b.billMedicalTime = TO_DATE(:billMedicalTime, 'YYYY-MM-DD') AND b.billStatus = 1 ) AND s.scheduleStatus = 1 ORDER BY s.scheduleId ASC")
	public List<Schedule> findAllScheduleBySpecialistIdAndByBillMedicalTimeAndStatusActive(@Param("specialistId") long specialistId, @Param("billMedicalTime") String billMedicalTime);
}
