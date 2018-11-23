package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;
import java.time.LocalDate;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="BILL")
@Proxy(lazy = false)
public class Bill implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="BILL_SEQ")
	@SequenceGenerator(name="BILL_SEQ", sequenceName="BILL_SEQ", allocationSize=1)
	@Column(name="BILL_ID")
	private long billId;
	
	@Column(name="BILL_MEDICAL_TIME")
	private LocalDate billMedicalTime;
	
	@Column(name="BILL_COMMISSIONS")
	private int billCommissions;
	
	@Column(name="BILL_SALARY")
	private int billSalary;
	
	@Column(name="BILL_STATUS")
	private char billStatus;
	
	@JoinColumn(name = "FORECAST_ID", referencedColumnName = "FORECAST_ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Forecast forecastId;
	
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "PATIENT_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Patient patientId;
    
    @JoinColumn(name = "PAYMENT_TYPE_ID", referencedColumnName = "PAYMENT_TYPE_ID")
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private PaymentType paymentTypeId;
    
    @JoinColumn(name = "SCHEDULE_ID", referencedColumnName = "SCHEDULE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Schedule scheduleId;
    
    @JoinColumn(name = "SPECIALIST_ID", referencedColumnName = "SPECIALIST_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Specialist specialistId;
    
    @JoinColumn(name = "STATE_MEDICAL_TIME_ID", referencedColumnName = "STATE_MEDICAL_TIME_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private StateMedicalTime stateMedicalTimeId;
	
	public Bill() {
		
	}

	public Bill(long billId) {
		this.billId = billId;
	}
	
	public Bill(long billId, LocalDate billMedicalTime, int billCommissions, int billSalary, char billStatus) {
		this.billId = billId;
		this.billMedicalTime = billMedicalTime;
		this.billCommissions = billCommissions;
		this.billSalary = billSalary;
		this.billStatus = billStatus;
	}
	
	public long getBillId() {
		return billId;
	}
	public void setBillId(long billId) {
		this.billId = billId;
	}
	public LocalDate getBillMedicalTime() {
		return billMedicalTime;
	}
	public void setBillMedicalTime(LocalDate billMedicalTime) {
		this.billMedicalTime = billMedicalTime;
	}
	public int getBillCommissions() {
		return billCommissions;
	}
	public void setBillCommissions(int billCommissions) {
		this.billCommissions = billCommissions;
	}
	public int getBillSalary() {
		return billSalary;
	}
	public void setBillSalary(int billSalary) {
		this.billSalary = billSalary;
	}

	
	public char getBillStatus() {
		return billStatus;
	}

	public void setBillStatus(char billStatus) {
		this.billStatus = billStatus;
	}

	public Forecast getForecastId() {
		return forecastId;
	}

	public void setForecastId(Forecast forecastId) {
		this.forecastId = forecastId;
	}

	public Patient getPatientId() {
		return patientId;
	}

	public void setPatientId(Patient patientId) {
		this.patientId = patientId;
	}

	public PaymentType getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(PaymentType paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}

	public Schedule getScheduleId() {
		return scheduleId;
	}

	public void setScheduleId(Schedule scheduleId) {
		this.scheduleId = scheduleId;
	}

	public Specialist getSpecialistId() {
		return specialistId;
	}

	public void setSpecialistId(Specialist specialistId) {
		this.specialistId = specialistId;
	}

	public StateMedicalTime getStateMedicalTimeId() {
		return stateMedicalTimeId;
	}

	public void setStateMedicalTimeId(StateMedicalTime stateMedicalTimeId) {
		this.stateMedicalTimeId = stateMedicalTimeId;
	}
	
}
