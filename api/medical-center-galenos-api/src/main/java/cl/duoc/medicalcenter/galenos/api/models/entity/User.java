package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;
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
@Table(name="\"USER\"")
@Proxy(lazy = false)
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="USER_SEQ")
	@SequenceGenerator(name="USER_SEQ", sequenceName="USER_SEQ", allocationSize=1)
	@Column(name="USER_ID")
	private long userId;
	
	@Column(name="USER_PASSWORD")
	private String userPassword;
	
	@Column(name="USER_STATUS")
	private char userStatus;
	
    @JoinColumn(name = "PATIENT_ID", referencedColumnName = "PATIENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Patient patientId;
    
    @JoinColumn(name = "PERSONAL_ID", referencedColumnName = "PERSONAL_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Personal personalId;
    
    @JoinColumn(name = "PROFILE_ID", referencedColumnName = "PROFILE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Profile profileId;
    
	
    @JoinColumn(name = "SPECIALIST_ID", referencedColumnName = "SPECIALIST_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Specialist specialistId;
    
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
    private List<ExceptionLog> exceptionLogList;
    
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userId", fetch = FetchType.LAZY)
    private List<EventLog> eventLogList;
	
	public User() {
		
	}

	public User(long userId) {
		this.userId = userId;
	}

	public User(long userId, String userPassword, char userStatus) {
		this.userId = userId;
		this.userPassword = userPassword;
		this.userStatus = userStatus;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public char getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(char userStatus) {
		this.userStatus = userStatus;
	}

	public Patient getPatientId() {
		return patientId;
	}

	public void setPatientId(Patient patientId) {
		this.patientId = patientId;
	}

	public Profile getProfileId() {
		return profileId;
	}

	public void setProfileId(Profile profileId) {
		this.profileId = profileId;
	}

	public Specialist getSpecialistId() {
		return specialistId;
	}

	public void setSpecialistId(Specialist specialistId) {
		this.specialistId = specialistId;
	}

	public List<ExceptionLog> getExceptionLogList() {
		return exceptionLogList;
	}

	public void setExceptionLogList(List<ExceptionLog> exceptionLogList) {
		this.exceptionLogList = exceptionLogList;
	}

	public List<EventLog> getEventLogList() {
		return eventLogList;
	}

	public void setEventLogList(List<EventLog> eventLogList) {
		this.eventLogList = eventLogList;
	}

	public Personal getPersonalId() {
		return personalId;
	}

	public void setPersonalId(Personal personalId) {
		this.personalId = personalId;
	}
}
