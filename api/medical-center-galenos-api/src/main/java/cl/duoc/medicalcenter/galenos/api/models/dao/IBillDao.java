package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cl.duoc.medicalcenter.galenos.api.models.entity.Bill;

public interface IBillDao extends JpaRepository<Bill, Long> {

	@Query("SELECT b FROM Bill b WHERE b.stateMedicalTimeId.stateMedicalTimeId = 1 AND b.billStatus = 1 ORDER BY b.billId ASC")
	public List<Bill> findAllByStateMedicalTimeReservedAndStatusActive(Pageable pageable);
	
	@Query("SELECT b FROM Bill b WHERE b.stateMedicalTimeId.stateMedicalTimeId = 1 AND b.billStatus = 1 ORDER BY b.billId ASC")
	public List<Bill> findAllByStateMedicalTimeReservedAndStatusActiveCount();
	
	@Query("SELECT b FROM Bill b WHERE b.patientId.patientRun = :patientRun AND b.stateMedicalTimeId.stateMedicalTimeId = 1 AND b.billStatus = 1 ORDER BY b.billId ASC")
	public List<Bill> findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(@Param("patientRun") String patientRun, Pageable pageable);
	
	@Query("SELECT b FROM Bill b WHERE b.patientId.patientRun = :patientRun AND b.stateMedicalTimeId.stateMedicalTimeId = 1 AND b.billStatus = 1 ORDER BY b.billId ASC")
	public List<Bill> findAllByStateMedicalTimeReservedAndPatientRunAndStatusActiveCount(@Param("patientRun") String patientRun);
	
	@Modifying
	@Query("UPDATE Bill b SET b.paymentTypeId.paymentTypeId = :paymentTypeId , b.forecastId.forecastId = :forecastId , b.stateMedicalTimeId.stateMedicalTimeId = 2, b.billCommissions = :billCommissions, b.billSalary = :billSalary WHERE b.billId = :billId")
	public int updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary(@Param("paymentTypeId") long paymentTypeId, @Param("forecastId") long forecastId, @Param("billCommissions") int billCommissions, @Param("billSalary") int billSalary, @Param("billId") long billId);
	
	@Query("SELECT b FROM Bill b WHERE b.patientId.patientRun = :patientRun AND b.billStatus = 1 ORDER BY b.billId ASC")
	public List<Bill> findAllByPatientRunAndStatusActive(@Param("patientRun") String patientRun, Pageable pageable);
	
	@Query("SELECT b FROM Bill b WHERE b.patientId.patientRun = :patientRun AND b.billStatus = 1 ORDER BY b.billId ASC")
	public List<Bill> findAllByPatientRunAndStatusActiveCount(@Param("patientRun") String patientRun);
	
	@Modifying
	@Query("UPDATE Bill b SET b.billStatus = 0 WHERE b.billId = :billId")
	public int updateBillByBillIdToBillStatusInactivo(@Param("billId") long billId);
	
	@Query("SELECT b FROM Bill b WHERE b.billId = :billId AND b.billStatus = 1")
	public Bill findByBillIdAndStatusActive(@Param("billId") long billId);
	
	@Query("SELECT b FROM Bill b WHERE b.stateMedicalTimeId.stateMedicalTimeId = 2 AND b.billStatus = 1 AND b.specialistId.specialistId = :specialistId ORDER BY b.billId ASC")
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(@Param("specialistId") long specialistId, Pageable pageable);
	
	@Query("SELECT b FROM Bill b WHERE b.stateMedicalTimeId.stateMedicalTimeId = 2 AND b.billStatus = 1 AND b.specialistId.specialistId = :specialistId ORDER BY b.billId ASC")
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActiveCount(@Param("specialistId") long specialistId);
	
	@Query("SELECT b FROM Bill b WHERE b.stateMedicalTimeId.stateMedicalTimeId = 3 AND b.billStatus = 1 AND b.specialistId.specialistId = :specialistId ORDER BY b.billId ASC")
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(@Param("specialistId") long specialistId, Pageable pageable);
	
	@Query("SELECT b FROM Bill b WHERE b.stateMedicalTimeId.stateMedicalTimeId = 3 AND b.billStatus = 1 AND b.specialistId.specialistId = :specialistId ORDER BY b.billId ASC")
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActiveCount(@Param("specialistId") long specialistId);
	
	@Modifying
	@Query("UPDATE Bill b SET b.stateMedicalTimeId.stateMedicalTimeId = 3 WHERE b.billId = :billId")
	public int updateBillByBillIdToStateMedicalTimeAttended(@Param("billId") long billId);
	
	@Query("SELECT b FROM Bill b WHERE b.stateMedicalTimeId.stateMedicalTimeId = 1 AND b.billStatus = 1 ORDER BY b.billId ASC")
	public List<Bill> findAllByStateMedicalTimeReservedRecordationAndStatusActive();
	
	@Query("SELECT ROUND(SUM((b.billSalary * 70) / 100)), TO_CHAR(b.billMedicalTime, 'MONTH') FROM Bill b WHERE b.billStatus = 1 AND b.specialistId.specialistId = :specialistId AND b.billMedicalTime BETWEEN TO_DATE(:dateStart, 'YYYY-MM-DD') AND TO_DATE(:dateEnd, 'YYYY-MM-DD') GROUP BY TO_CHAR(b.billMedicalTime, 'MONTH')")
	public List<String> findBySpecialistIdAndDateStartAndDateEndAndStatusActive(@Param("specialistId") long specialistId, @Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);
	
	@Query("SELECT ROUND(SUM((b.billSalary * 70) / 100)), TO_CHAR(b.billMedicalTime, 'MONTH') FROM Bill b WHERE b.billStatus = 1 AND b.billMedicalTime BETWEEN TO_DATE(:dateStart, 'YYYY-MM-DD') AND TO_DATE(:dateEnd, 'YYYY-MM-DD') GROUP BY TO_CHAR(b.billMedicalTime, 'MONTH')")
	public List<String> findByDateStartAndDateEndAndStatusActive(@Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);

	@Query("SELECT b FROM Bill b WHERE specialistId.specialistId = :specialistId AND b.billStatus = 1 AND b.billMedicalTime BETWEEN TO_DATE(:dateStart, 'YYYY-MM-DD') AND TO_DATE(:dateEnd, 'YYYY-MM-DD') ")
	public List<Bill> findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport(@Param("specialistId") long specialistId, @Param("dateStart") String dateStart, @Param("dateEnd") String dateEnd);
}
