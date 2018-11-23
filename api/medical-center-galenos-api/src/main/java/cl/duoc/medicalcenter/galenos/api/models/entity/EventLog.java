package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Proxy;

@Entity
@Table(name="EVENT_LOG")
@Proxy(lazy = false)
public class EventLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="EVENT_LOG_SEQ")
	@SequenceGenerator(name="EVENT_LOG_SEQ", sequenceName="EVENT_LOG_SEQ", allocationSize=1)
	@Column(name="EVENT_LOG_ID")
	private long eventLogId;
	
	@Column(name="EVENT_LOG_DESCRIPTION")
	private String eventLogDescription;
	
	@Temporal(TemporalType.DATE)
	@Column(name="EVENT_LOG_DATE")
	private Date eventLogDate;
	
	@Column(name="EVENT_LOG_STATUS_CODE")
	private int eventLogStatusCode;
	
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;
	
	public EventLog() {
		
	}
	public EventLog(long eventLogId) {
		this.eventLogId = eventLogId;
	}
	public EventLog(long eventLogId, String eventLogDescription, Date eventLogDate, int eventLogStatusCode) {
		this.eventLogId = eventLogId;
		this.eventLogDescription = eventLogDescription;
		this.eventLogDate = eventLogDate;
		this.eventLogStatusCode = eventLogStatusCode;
	}
	public long getEventLogId() {
		return eventLogId;
	}
	public void setEventLogId(long eventLogId) {
		this.eventLogId = eventLogId;
	}
	public String getEventLogDescription() {
		return eventLogDescription;
	}
	public void setEventLogDescription(String eventLogDescription) {
		this.eventLogDescription = eventLogDescription;
	}
	public Date getEventLogDate() {
		return eventLogDate;
	}
	public void setEventLogDate(Date eventLogDate) {
		this.eventLogDate = eventLogDate;
	}
	public int getEventLogStatusCode() {
		return eventLogStatusCode;
	}
	public void setEventLogStatusCode(int eventLogStatusCode) {
		this.eventLogStatusCode = eventLogStatusCode;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
	
}
