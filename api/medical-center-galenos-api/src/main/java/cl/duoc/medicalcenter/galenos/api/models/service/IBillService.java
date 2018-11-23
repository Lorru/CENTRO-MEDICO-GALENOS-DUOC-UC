package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import cl.duoc.medicalcenter.galenos.api.models.entity.Bill;

public interface IBillService {
	
	public Bill save(Bill bill);
	
	public List<Bill> findAllByStateMedicalTimeReservedAndStatusActive(Pageable pageable);
	
	public List<Bill> findAllByStateMedicalTimeReservedAndStatusActiveCount();
	
	public List<Bill> findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(String patientRun, Pageable pageable);
	
	public List<Bill> findAllByStateMedicalTimeReservedAndPatientRunAndStatusActiveCount(String patientRun);
	
	public int updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary(long paymentTypeId, long forecastId, int billCommissions, int billSalary, long billId);
	
	public List<Bill> findAllByPatientRunAndStatusActive(String patientRun, Pageable pageable);
	
	public List<Bill> findAllByPatientRunAndStatusActiveCount(String patientRun);
	
	public int updateBillByBillIdToBillStatusInactivo(long billId);
	
	public Bill findByBillIdAndStatusActive(long billId);
	
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(long specialistId, Pageable pageable);
	
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActiveCount(long specialistId);
	
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(long specialistId, Pageable pageable);
	
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActiveCount(long specialistId);
	
	public int updateBillByBillIdToStateMedicalTimeAttended(long billId);
	
	public List<Bill> findAllByStateMedicalTimeReservedRecordationAndStatusActive();
	
	public List<String> findBySpecialistIdAndDateStartAndDateEndAndStatusActive(long specialistId, String dateStart, String dateEnd);
	
	public List<String> findByDateStartAndDateEndAndStatusActive(String dateStart, String dateEnd);
	
	public List<Bill> findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport(long specialistId, String dateStart, String dateEnd);
}
