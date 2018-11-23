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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="GENDER")
@Proxy(lazy = false)
public class Gender implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="GENDER_ID")
	private long genderId;
	
	@Column(name="GENDER_DESCRIPTION")
	private String genderDescription;
	
	@JsonIgnore
	@Column(name="GENDER_STATUS")
	private char genderStatus;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genderId", fetch = FetchType.LAZY)
    private List<Patient> patientList;
    
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "genderId", fetch = FetchType.LAZY)
    private List<Specialist> specialistList;
	
	public Gender() {
		
	}
	public Gender(long genderId) {
		this.genderId = genderId;
	}
	public Gender(long genderId, String genderDescription, char genderStatus) {
		this.genderId = genderId;
		this.genderDescription = genderDescription;
		this.genderStatus = genderStatus;
	}
	public long getGenderId() {
		return genderId;
	}
	public void setGenderId(long genderId) {
		this.genderId = genderId;
	}
	public String getGenderDescription() {
		return genderDescription;
	}
	public void setGenderDescription(String genderDescription) {
		this.genderDescription = genderDescription;
	}
	public char getGenderStatus() {
		return genderStatus;
	}
	public void setGenderStatus(char genderStatus) {
		this.genderStatus = genderStatus;
	}
	public List<Patient> getPatientList() {
		return patientList;
	}
	public void setPatientList(List<Patient> patientList) {
		this.patientList = patientList;
	}
	public List<Specialist> getSpecialistList() {
		return specialistList;
	}
	public void setSpecialistList(List<Specialist> specialistList) {
		this.specialistList = specialistList;
	}
	
}
