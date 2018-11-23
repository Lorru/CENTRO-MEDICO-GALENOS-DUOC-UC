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
@Table(name="SPECIALIST")
@Proxy(lazy = false)
public class Specialist implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="SPECIALIST_SEQ")
	@SequenceGenerator(name="SPECIALIST_SEQ", sequenceName="SPECIALIST_SEQ", allocationSize=1)
	@Column(name="SPECIALIST_ID")
	private long specialistId;
	
	@Column(name="SPECIALIST_RUN")
	private String specialistRun;
	
	@Column(name="SPECIALIST_FIRST_NAME")
	private String specialistFirstName;
	
	@Column(name="SPECIALIST_SECOND_NAME")
	private String specialistSecondName;
	
	@Column(name="SPECIALIST_SURNAME")
	private String specialistSurName;
	
	@Column(name="SPECIALIST_SECOND_SURNAME")
	private String specialistSecondSurName;
	
	@Column(name="SPECIALIST_FULL_NAME")
	private String specialistFullName;
	
	@Column(name="SPECIALIST_BIRTHDATE")
	private LocalDate specialistBirthDate;
	
	@Column(name="SPECIALIST_EMAIL")
	private String specialistEmail;
	
	@Column(name="SPECIALIST_STATUS")
	private char specialistStatus;
	
	@JsonIgnore
    @OneToMany(mappedBy = "specialistId", fetch = FetchType.LAZY)
    private List<User> userList;
    
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "specialistId", fetch = FetchType.LAZY)
    private List<Bill> billList;
    
    @JoinColumn(name = "GENDER_ID", referencedColumnName = "GENDER_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gender genderId;
    
	
    @JoinColumn(name = "SPECIALTY_ID", referencedColumnName = "SPECIALTY_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Specialty specialtyId;
	
	
    @JoinColumn(name = "BRANCH_OFFICE_ID", referencedColumnName = "BRANCH_OFFICE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BranchOffice branchOfficeId;
	
	public Specialist() {
		
	}
	
	public Specialist(long specialistId) {
		this.specialistId = specialistId;
	}
	
	public Specialist(long specialistId, String specialistRun, String specialistFirstName, String specialistSecondName, String specialistSurName, String specialistSecondSurName, String specialistFullName, LocalDate specialistBirthDate, String specialistEmail, char specialistStatus) {
		this.specialistId = specialistId;
		this.specialistRun = specialistRun;
		this.specialistFirstName = specialistFirstName;
		this.specialistSecondName = specialistSecondName;
		this.specialistSurName = specialistSurName;
		this.specialistSecondSurName = specialistSecondSurName;
		this.specialistFullName = specialistFullName;
		this.specialistBirthDate = specialistBirthDate;
		this.specialistEmail = specialistEmail;
		this.specialistStatus = specialistStatus;
	}
	
	public long getSpecialistId() {
		return specialistId;
	}
	public void setSpecialistId(long specialistId) {
		this.specialistId = specialistId;
	}
	public String getSpecialistRun() {
		return specialistRun;
	}
	public void setSpecialistRun(String specialistRun) {
		this.specialistRun = specialistRun;
	}
	public String getSpecialistFirstName() {
		return specialistFirstName;
	}
	public void setSpecialistFirstName(String specialistFirstName) {
		this.specialistFirstName = specialistFirstName;
	}
	public String getSpecialistSecondName() {
		return specialistSecondName;
	}
	public void setSpecialistSecondName(String specialistSecondName) {
		this.specialistSecondName = specialistSecondName;
	}
	public String getSpecialistSurName() {
		return specialistSurName;
	}
	public void setSpecialistSurName(String specialistSurName) {
		this.specialistSurName = specialistSurName;
	}
	public String getSpecialistSecondSurName() {
		return specialistSecondSurName;
	}
	public void setSpecialistSecondSurName(String specialistSecondSurName) {
		this.specialistSecondSurName = specialistSecondSurName;
	}
	public String getSpecialistFullName() {
		return specialistFullName;
	}
	public void setSpecialistFullName(String specialistFullName) {
		this.specialistFullName = specialistFullName;
	}
	public LocalDate getSpecialistBirthDate() {
		return specialistBirthDate;
	}
	public void setSpecialistBirthDate(LocalDate specialistBirthDate) {
		this.specialistBirthDate = specialistBirthDate;
	}
	public String getSpecialistEmail() {
		return specialistEmail;
	}
	public void setSpecialistEmail(String specialistEmail) {
		this.specialistEmail = specialistEmail;
	}
	public char getSpecialistStatus() {
		return specialistStatus;
	}
	public void setSpecialistStatus(char specialistStatus) {
		this.specialistStatus = specialistStatus;
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

	public Specialty getSpecialtyId() {
		return specialtyId;
	}

	public void setSpecialtyId(Specialty specialtyId) {
		this.specialtyId = specialtyId;
	}

	public BranchOffice getBranchOfficeId() {
		return branchOfficeId;
	}

	public void setBranchOfficeId(BranchOffice branchOfficeId) {
		this.branchOfficeId = branchOfficeId;
	}
	
	
}
