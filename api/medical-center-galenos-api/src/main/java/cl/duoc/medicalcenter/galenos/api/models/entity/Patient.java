package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="PATIENT")
@Proxy(lazy = false)
public class Patient implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="PATIENT_SEQ")
	@SequenceGenerator(name="PATIENT_SEQ", sequenceName="PATIENT_SEQ", allocationSize=1)
	@Column(name="PATIENT_ID")
	private long patientId;
	
	@Column(name="PATIENT_RUN")
	private String patientRun;
	
	@Column(name="PATIENT_FIRST_NAME")
	private String patientFirstName;
	
	@Column(name="PATIENT_SECOND_NAME")
	private String patientSecondName;
	
	@Column(name="PATIENT_SURNAME")
	private String patientSurName;
	
	@Column(name="PATIENT_SECOND_SURNAME")
	private String patientSecondSurName;
	
	@Column(name="PATIENT_FULL_NAME")
	private String patientFullName;
	
	@Column(name="PATIENT_BIRTHDATE")
	private LocalDate patientBirthDate;
	
	@Column(name="PATIENT_EMAIL")
	private String patientEmail;
	
	@JsonIgnore
	@Column(name="PATIENT_STATUS")
	private char patientStatus;
	
	@JsonIgnore
    @OneToMany(mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<User> userList;
    
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "patientId", fetch = FetchType.LAZY)
    private List<Bill> billList;
    
    @JoinColumn(name = "GENDER_ID", referencedColumnName = "GENDER_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gender genderId;
	
	public Patient() {
		
	}
	public Patient(long patientId) {
		this.patientId = patientId;
	}
	public Patient(long patientId, String patientRun, String patientFirstName, String patientSecondName, String patientSurName, String patientSecondSurName, String patientFullName, LocalDate patientBirthDate, String patientEmail, char patientStatus) {
		this.patientId = patientId;
		this.patientRun = patientRun;
		this.patientFirstName = patientFirstName;
		this.patientSecondName = patientSecondName;
		this.patientSurName = patientSurName;
		this.patientSecondSurName = patientSecondSurName;
		this.patientFullName = patientFullName;
		this.patientBirthDate = patientBirthDate;
		this.patientEmail = patientEmail;
		this.patientStatus = patientStatus;
	}
	public long getPatientId() {
		return patientId;
	}
	public void setPatientId(long patientId) {
		this.patientId = patientId;
	}
	public String getPatientRun() {
		return patientRun;
	}
	public void setPatientRun(String patientRun) {
		this.patientRun = patientRun;
	}
	public String getPatientFirstName() {
		return patientFirstName;
	}
	public void setPatientFirstName(String patientFirstName) {
		this.patientFirstName = patientFirstName;
	}
	public String getPatientSecondName() {
		return patientSecondName;
	}
	public void setPatientSecondName(String patientSecondName) {
		this.patientSecondName = patientSecondName;
	}
	public String getPatientSurName() {
		return patientSurName;
	}
	public void setPatientSurName(String patientSurName) {
		this.patientSurName = patientSurName;
	}
	public String getPatientSecondSurName() {
		return patientSecondSurName;
	}
	public void setPatientSecondSurName(String patientSecondSurName) {
		this.patientSecondSurName = patientSecondSurName;
	}
	public String getPatientFullName() {
		return patientFullName;
	}
	public void setPatientFullName(String patientFullName) {
		this.patientFullName = patientFullName;
	}
	public LocalDate getPatientBirthDate() {
		return patientBirthDate;
	}
	public void setPatientBirthDate(LocalDate patientBirthDate) {
		this.patientBirthDate = patientBirthDate;
	}
	public String getPatientEmail() {
		return patientEmail;
	}
	public void setPatientEmail(String patientEmail) {
		this.patientEmail = patientEmail;
	}
	public char getPatientStatus() {
		return patientStatus;
	}
	public void setPatientStatus(char patientStatus) {
		this.patientStatus = patientStatus;
	}
	public List<User> getUserList() {
		return userList;
	}
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	public List<Bill> getBillList() {
		return billList;
	}
	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}
	public Gender getGenderId() {
		return genderId;
	}
	public void setGenderId(Gender genderId) {
		this.genderId = genderId;
	}	
	
}
