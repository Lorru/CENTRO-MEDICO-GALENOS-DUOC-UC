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
@Table(name="SCHEDULE")
@Proxy(lazy = false)
public class Schedule implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="SCHEDULE_ID")
	private long scheduleId;
	
	@Column(name="SCHEDULE_HOUR")
	private String scheduleHour;
	
	@Column(name="SCHEDULE_STATUS")
	private char scheduleStatus;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "scheduleId", fetch = FetchType.LAZY)
    private List<Bill> billList;
	
	public Schedule() {
		
	}

	public Schedule(long scheduleId) {
		this.scheduleId = scheduleId;
	}
	
	public Schedule(long scheduleId, String scheduleHour, char scheduleStatus) {
		this.scheduleId = scheduleId;
		this.scheduleHour = scheduleHour;
		this.scheduleStatus = scheduleStatus;
	}
	
	public long getScheduleId() {
		return scheduleId;
	}
	public void setScheduleId(long scheduleId) {
		this.scheduleId = scheduleId;
	}
	public String getScheduleHour() {
		return scheduleHour;
	}
	public void setScheduleHour(String scheduleHour) {
		this.scheduleHour = scheduleHour;
	}
	public char getScheduleStatus() {
		return scheduleStatus;
	}
	public void setScheduleStatus(char scheduleStatus) {
		this.scheduleStatus = scheduleStatus;
	}

	public List<Bill> getBillList() {
		return billList;
	}

	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}
	
}
