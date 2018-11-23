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
@Table(name="SPECIALTY")
@Proxy(lazy = false)
public class Specialty implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="SPECIALTY_ID")
	private long specialtyId;
	
	@Column(name="SPECIALTY_DESCRIPTION")
	private String specialtyDescription;
	
	@Column(name="SPECIALTY_AMOUNT")
	private int specialtyAmount;
	
	@Column(name="SPECIALTY_STATUS")
	private char specialtyStatus;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialtyId", fetch = FetchType.LAZY)
    private List<BranchOfficeSpecialty> branchOfficeSpecialtyList;
    
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialtyId", fetch = FetchType.LAZY)
    private List<Specialist> specialistList;
	
	public Specialty() {
		
	}

	public Specialty(long specialtyId) {
		this.specialtyId = specialtyId;
	}

	public Specialty(long specialtyId, String specialtyDescription, int specialtyAmount, char specialtyStatus) {
		this.specialtyId = specialtyId;
		this.specialtyDescription = specialtyDescription;
		this.specialtyAmount = specialtyAmount;
		this.specialtyStatus = specialtyStatus;
	}
	
	public long getSpecialtyId() {
		return specialtyId;
	}
	public void setSpecialtyId(long specialtyId) {
		this.specialtyId = specialtyId;
	}
	public String getSpecialtyDescription() {
		return specialtyDescription;
	}
	public void setSpecialtyDescription(String specialtyDescription) {
		this.specialtyDescription = specialtyDescription;
	}
	
	public int getSpecialtyAmount() {
		return specialtyAmount;
	}

	public void setSpecialtyAmount(int specialtyAmount) {
		this.specialtyAmount = specialtyAmount;
	}

	public char getSpecialtyStatus() {
		return specialtyStatus;
	}
	public void setSpecialtyStatus(char specialtyStatus) {
		this.specialtyStatus = specialtyStatus;
	}

	public List<BranchOfficeSpecialty> getBranchOfficeSpecialtyList() {
		return branchOfficeSpecialtyList;
	}

	public void setBranchOfficeSpecialtyList(List<BranchOfficeSpecialty> branchOfficeSpecialtyList) {
		this.branchOfficeSpecialtyList = branchOfficeSpecialtyList;
	}

	public List<Specialist> getSpecialistList() {
		return specialistList;
	}

	public void setSpecialistList(List<Specialist> specialistList) {
		this.specialistList = specialistList;
	}
	
}
