package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IBillDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.Bill;

@Service
public class BillServiceImpl implements IBillService {

	@Autowired
	private IBillDao iBillDao;
	
	

	@Override
	@Transactional
	public Bill save(Bill bill) {
		return iBillDao.save(bill);
	}


	@Override
	@Transactional
	public List<Bill> findAllByStateMedicalTimeReservedAndStatusActive(Pageable pageable) {
		return iBillDao.findAllByStateMedicalTimeReservedAndStatusActive(pageable);
	}


	@Override
	@Transactional
	public List<Bill> findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(String patientRun, Pageable pageable) {
		return iBillDao.findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(patientRun, pageable);
	}


	@Override
	@Transactional
	public List<Bill> findAllByStateMedicalTimeReservedAndStatusActiveCount() {
		return iBillDao.findAllByStateMedicalTimeReservedAndStatusActiveCount();
	}


	@Override
	@Transactional
	public List<Bill> findAllByStateMedicalTimeReservedAndPatientRunAndStatusActiveCount(String patientRun) {
		return iBillDao.findAllByStateMedicalTimeReservedAndPatientRunAndStatusActiveCount(patientRun);
	}


	@Override
	@Transactional
	public int updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary(long paymentTypeId, long forecastId, int billCommissions, int billSalary, long billId) {
		return iBillDao.updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary(paymentTypeId, forecastId, billCommissions, billSalary, billId);
	}


	@Override
	@Transactional
	public List<Bill> findAllByPatientRunAndStatusActive(String patientRun, Pageable pageable) {
		return iBillDao.findAllByPatientRunAndStatusActive(patientRun, pageable);
	}


	@Override
	@Transactional
	public List<Bill> findAllByPatientRunAndStatusActiveCount(String patientRun) {
		return iBillDao.findAllByPatientRunAndStatusActiveCount(patientRun);
	}


	@Override
	@Transactional
	public int updateBillByBillIdToBillStatusInactivo(long billId) {
		return iBillDao.updateBillByBillIdToBillStatusInactivo(billId);
	}


	@Override
	@Transactional
	public Bill findByBillIdAndStatusActive(long billId) {
		return iBillDao.findByBillIdAndStatusActive(billId);
	}


	@Override
	@Transactional
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(long specialistId, Pageable pageable) {
		return iBillDao.findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(specialistId, pageable);
	}


	@Override
	@Transactional
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActiveCount(long specialistId) {
		return iBillDao.findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActiveCount(specialistId);
	}


	@Override
	@Transactional
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(long specialistId, Pageable pageable) {
		return iBillDao.findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(specialistId, pageable);
	}


	@Override
	@Transactional
	public List<Bill> findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActiveCount(long specialistId) {
		return iBillDao.findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActiveCount(specialistId);
	}


	@Override
	@Transactional
	public int updateBillByBillIdToStateMedicalTimeAttended(long billId) {
		return iBillDao.updateBillByBillIdToStateMedicalTimeAttended(billId);
	}


	@Override
	@Transactional
	public List<Bill> findAllByStateMedicalTimeReservedRecordationAndStatusActive() {
		return iBillDao.findAllByStateMedicalTimeReservedRecordationAndStatusActive();
	}


	@Override
	@Transactional
	public List<String> findBySpecialistIdAndDateStartAndDateEndAndStatusActive(long specialistId, String dateStart, String dateEnd) {
		return iBillDao.findBySpecialistIdAndDateStartAndDateEndAndStatusActive(specialistId, dateStart, dateEnd);
	}


	@Override
	@Transactional
	public List<String> findByDateStartAndDateEndAndStatusActive(String dateStart, String dateEnd) {
		return iBillDao.findByDateStartAndDateEndAndStatusActive(dateStart, dateEnd);
	}


	@Override
	@Transactional
	public List<Bill> findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport(long specialistId, String dateStart, String dateEnd) {
		return iBillDao.findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport(specialistId, dateStart, dateEnd);
	}

}
