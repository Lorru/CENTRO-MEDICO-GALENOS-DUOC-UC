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
@Table(name="STATE_MEDICAL_TIME")
@Proxy(lazy = false)
public class StateMedicalTime implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="STATE_MEDICAL_TIME_ID")
	private long stateMedicalTimeId;
	
	@Column(name="STATE_MEDICAL_TIME_DESCRIPTION")
	private String stateMedicalTimeDescription;
	
	@Column(name="STATE_MEDICAL_TIME_STATUS")
	private char stateMedicalTimeStatus;

	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stateMedicalTimeId", fetch = FetchType.LAZY)
    private List<Bill> billList;
	
	public StateMedicalTime() {
		
	}

	public StateMedicalTime(long stateMedicalTimeId) {
		this.stateMedicalTimeId = stateMedicalTimeId;
	}

	public StateMedicalTime(long stateMedicalTimeId, String stateMedicalTimeDescription, char stateMedicalTimeStatus) {
		this.stateMedicalTimeId = stateMedicalTimeId;
		this.stateMedicalTimeDescription = stateMedicalTimeDescription;
		this.stateMedicalTimeStatus = stateMedicalTimeStatus;
	}

	public long getStateMedicalTimeId() {
		return stateMedicalTimeId;
	}

	public void setStateMedicalTimeId(long stateMedicalTimeId) {
		this.stateMedicalTimeId = stateMedicalTimeId;
	}

	public String getStateMedicalTimeDescription() {
		return stateMedicalTimeDescription;
	}

	public void setStateMedicalTimeDescription(String stateMedicalTimeDescription) {
		this.stateMedicalTimeDescription = stateMedicalTimeDescription;
	}

	public char getStateMedicalTimeStatus() {
		return stateMedicalTimeStatus;
	}

	public void setStateMedicalTimeStatus(char stateMedicalTimeStatus) {
		this.stateMedicalTimeStatus = stateMedicalTimeStatus;
	}

	public List<Bill> getBillList() {
		return billList;
	}

	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}
	
}
