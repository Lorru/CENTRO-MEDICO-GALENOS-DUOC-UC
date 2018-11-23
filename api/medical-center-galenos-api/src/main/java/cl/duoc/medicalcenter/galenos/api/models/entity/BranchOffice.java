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
@Table(name="BRANCH_OFFICE")
@Proxy(lazy = false)
public class BranchOffice implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="BRANCH_OFFICE_ID")
	private long branchOfficeId;
	
	@Column(name="BRANCH_OFFICE_NAME")
	private String branchOfficeName;
	
	@Column(name="BRANCH_OFFICE_LOCATION")
	private String branchOfficeLocation;
	
	@Column(name="BRANCH_OFFICE_STATUS")
	private char branchOfficeStatus;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchOfficeId", fetch = FetchType.LAZY)
    private List<BranchOfficeSpecialty> branchOfficeSpecialtyList;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchOfficeId", fetch = FetchType.LAZY)
    private List<BranchOfficeCategory> branchOfficeCategoryList;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "branchOfficeId", fetch = FetchType.LAZY)
    private List<Specialist> specialistList;
	
	public BranchOffice() {
		
	}

	public BranchOffice(long branchOfficeId) {
		this.branchOfficeId = branchOfficeId;
	}

	public BranchOffice(long branchOfficeId, String branchOfficeName, String branchOfficeLocation, char branchOfficeStatus) {
		this.branchOfficeId = branchOfficeId;
		this.branchOfficeName = branchOfficeName;
		this.branchOfficeLocation = branchOfficeLocation;
		this.branchOfficeStatus = branchOfficeStatus;
	}
	
	public long getBranchOfficeId() {
		return branchOfficeId;
	}
	public void setBranchOfficeId(long branchOfficeId) {
		this.branchOfficeId = branchOfficeId;
	}
	public String getBranchOfficeName() {
		return branchOfficeName;
	}
	public void setBranchOfficeName(String branchOfficeName) {
		this.branchOfficeName = branchOfficeName;
	}
	public String getBranchOfficeLocation() {
		return branchOfficeLocation;
	}
	public void setBranchOfficeLocation(String branchOfficeLocation) {
		this.branchOfficeLocation = branchOfficeLocation;
	}
	public char getBranchOfficeStatus() {
		return branchOfficeStatus;
	}
	public void setBranchOfficeStatus(char branchOfficeStatus) {
		this.branchOfficeStatus = branchOfficeStatus;
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

	public List<BranchOfficeCategory> getBranchOfficeCategoryList() {
		return branchOfficeCategoryList;
	}

	public void setBranchOfficeCategoryList(List<BranchOfficeCategory> branchOfficeCategoryList) {
		this.branchOfficeCategoryList = branchOfficeCategoryList;
	}
	
}
