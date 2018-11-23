package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

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
@Table(name="PERSONAL")
@Proxy(lazy = false)
public class Personal implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="PERSONAL_SEQ")
	@SequenceGenerator(name="PERSONAL_SEQ", sequenceName="PERSONAL_SEQ", allocationSize=1)
	@Column(name="PERSONAL_ID")
	private long personalId;
	
	@Column(name="PERSONAL_RUN")
	private String personalRun;
	
	@Column(name="PERSONAL_FIRST_NAME")
	private String personalFirstName;
	
	@Column(name="PERSONAL_SECOND_NAME")
	private String personalSecondName;
	
	@Column(name="PERSONAL_SURNAME")
	private String personalSurName;
	
	@Column(name="PERSONAL_SECOND_SURNAME")
	private String personalSecondSurName;
	
	@Column(name="PERSONAL_FULL_NAME")
	private String personalFullName;
	
	@Column(name="PERSONAL_BIRTHDATE")
	private LocalDate personalBirthDate;
	
	@Column(name="PERSONAL_EMAIL")
	private String personalEmail;
	
	@Column(name="PERSONAL_STATUS")
	private char personalStatus;
	
	
    @JoinColumn(name = "BRANCH_OFFICE_ID", referencedColumnName = "BRANCH_OFFICE_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private BranchOffice branchOfficeId;
	
    @JoinColumn(name = "GENDER_ID", referencedColumnName = "GENDER_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Gender genderId;
    
    @JoinColumn(name = "CATEGORY_ID", referencedColumnName = "CATEGORY_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Category categoryId;
    
	@JsonIgnore
    @OneToMany(mappedBy = "personalId", fetch = FetchType.LAZY)
    private List<User> userList;

	public Personal() {
		
	}

	public Personal(long personalId) {
		super();
		this.personalId = personalId;
	}

	public Personal(long personalId, String personalRun, String personalFirstName, String personalSecondName,String personalSurName, String personalSecondSurName, String personalFullName, LocalDate personalBirthDate,String personalEmail, char personalStatus) {
		this.personalId = personalId;
		this.personalRun = personalRun;
		this.personalFirstName = personalFirstName;
		this.personalSecondName = personalSecondName;
		this.personalSurName = personalSurName;
		this.personalSecondSurName = personalSecondSurName;
		this.personalFullName = personalFullName;
		this.personalBirthDate = personalBirthDate;
		this.personalEmail = personalEmail;
		this.personalStatus = personalStatus;
	}

	public long getPersonalId() {
		return personalId;
	}

	public void setPersonalId(long personalId) {
		this.personalId = personalId;
	}

	public String getPersonalRun() {
		return personalRun;
	}

	public void setPersonalRun(String personalRun) {
		this.personalRun = personalRun;
	}

	public String getPersonalFirstName() {
		return personalFirstName;
	}

	public void setPersonalFirstName(String personalFirstName) {
		this.personalFirstName = personalFirstName;
	}

	public String getPersonalSecondName() {
		return personalSecondName;
	}

	public void setPersonalSecondName(String personalSecondName) {
		this.personalSecondName = personalSecondName;
	}

	public String getPersonalSurName() {
		return personalSurName;
	}

	public void setPersonalSurName(String personalSurName) {
		this.personalSurName = personalSurName;
	}

	public String getPersonalSecondSurName() {
		return personalSecondSurName;
	}

	public void setPersonalSecondSurName(String personalSecondSurName) {
		this.personalSecondSurName = personalSecondSurName;
	}

	public String getPersonalFullName() {
		return personalFullName;
	}

	public void setPersonalFullName(String personalFullName) {
		this.personalFullName = personalFullName;
	}

	public LocalDate getPersonalBirthDate() {
		return personalBirthDate;
	}

	public void setPersonalBirthDate(LocalDate personalBirthDate) {
		this.personalBirthDate = personalBirthDate;
	}

	public String getPersonalEmail() {
		return personalEmail;
	}

	public void setPersonalEmail(String personalEmail) {
		this.personalEmail = personalEmail;
	}

	public char getPersonalStatus() {
		return personalStatus;
	}

	public void setPersonalStatus(char personalStatus) {
		this.personalStatus = personalStatus;
	}

	public BranchOffice getBranchOfficeId() {
		return branchOfficeId;
	}

	public void setBranchOfficeId(BranchOffice branchOfficeId) {
		this.branchOfficeId = branchOfficeId;
	}

	public Gender getGenderId() {
		return genderId;
	}

	public void setGenderId(Gender genderId) {
		this.genderId = genderId;
	}

	public Category getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Category categoryId) {
		this.categoryId = categoryId;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
}
