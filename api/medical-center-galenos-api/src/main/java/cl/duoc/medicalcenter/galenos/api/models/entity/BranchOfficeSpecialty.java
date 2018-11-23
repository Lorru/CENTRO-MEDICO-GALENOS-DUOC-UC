package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="BRANCH_OFFICE_SPECIALTY")
@Proxy(lazy = false)
public class BranchOfficeSpecialty implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="BRANCH_OFFICE_SPECIALTY_ID")
	private long branchOfficeSpecialtyId;
	
    @JoinColumn(name = "BRANCH_OFFICE_ID", referencedColumnName = "BRANCH_OFFICE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BranchOffice branchOfficeId;
    
    @JoinColumn(name = "SPECIALTY_ID", referencedColumnName = "SPECIALTY_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Specialty specialtyId;
	
	public BranchOfficeSpecialty() {
		
	}

	public BranchOfficeSpecialty(long branchOfficeSpecialtyId) {
		this.branchOfficeSpecialtyId = branchOfficeSpecialtyId;
	}

	public long getBranchOfficeSpecialtyId() {
		return branchOfficeSpecialtyId;
	}

	public void setBranchOfficeSpecialtyId(long branchOfficeSpecialtyId) {
		this.branchOfficeSpecialtyId = branchOfficeSpecialtyId;
	}

	public BranchOffice getBranchOfficeId() {
		return branchOfficeId;
	}

	public void setBranchOfficeId(BranchOffice branchOfficeId) {
		this.branchOfficeId = branchOfficeId;
	}

	public Specialty getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(Specialty specialtyId) {
		this.specialtyId = specialtyId;
	}
	
}
