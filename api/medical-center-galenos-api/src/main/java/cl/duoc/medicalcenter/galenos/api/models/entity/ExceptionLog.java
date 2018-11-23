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
@Table(name="EXCEPTION_LOG")
@Proxy(lazy = false)
public class ExceptionLog implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE ,generator="EXCEPTION_LOG_SEQ")
	@SequenceGenerator(name="EXCEPTION_LOG_SEQ", sequenceName="EXCEPTION_LOG_SEQ", allocationSize=1)
	@Column(name="EXCEPTION_LOG_ID")
	private long exceptionLogId;
	
	@Column(name="EXCEPTION_LOG_DESCRIPTION")
	private String exceptionLogDescription;
	
	@Temporal(TemporalType.DATE)
	@Column(name="EXCEPTION_LOG_DATE")
	private Date exceptionLogDate;
	
	@Column(name="EXCEPTION_LOG_STATUS_CODE")
	private int exceptionLogStatusCode;
	
	@Column(name="EXCEPTION_LOG_CONTROLLER")
	private String exceptionLogController;
	
	@Column(name="EXCEPTION_LOG_METHOD")
	private String exceptionLogMethod;
	
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User userId;
	
	public ExceptionLog() {
		
	}
	public ExceptionLog(long exceptionLogId) {
		this.exceptionLogId = exceptionLogId;
	}
	
	
	public ExceptionLog(long exceptionLogId, String exceptionLogDescription, Date exceptionLogDate, int exceptionLogStatusCode, String exceptionLogController, String exceptionLogMethod, User userId) {
		this.exceptionLogId = exceptionLogId;
		this.exceptionLogDescription = exceptionLogDescription;
		this.exceptionLogDate = exceptionLogDate;
		this.exceptionLogStatusCode = exceptionLogStatusCode;
		this.exceptionLogController = exceptionLogController;
		this.exceptionLogMethod = exceptionLogMethod;
		this.userId = userId;
	}
	public long getExceptionLogId() {
		return exceptionLogId;
	}
	public void setExceptionLogId(long exceptionLogId) {
		this.exceptionLogId = exceptionLogId;
	}
	public String getExceptionLogDescription() {
		return exceptionLogDescription;
	}
	public void setExceptionLogDescription(String exceptionLogDescription) {
		this.exceptionLogDescription = exceptionLogDescription;
	}
	public Date getExceptionLogDate() {
		return exceptionLogDate;
	}
	public void setExceptionLogDate(Date exceptionLogDate) {
		this.exceptionLogDate = exceptionLogDate;
	}
	public int getExceptionLogStatusCode() {
		return exceptionLogStatusCode;
	}
	public void setExceptionLogStatusCode(int exceptionLogStatusCode) {
		this.exceptionLogStatusCode = exceptionLogStatusCode;
	}
	public String getExceptionLogController() {
		return exceptionLogController;
	}
	public void setExceptionLogController(String exceptionLogController) {
		this.exceptionLogController = exceptionLogController;
	}
	public String getExceptionLogMethod() {
		return exceptionLogMethod;
	}
	public void setExceptionLogMethod(String exceptionLogMethod) {
		this.exceptionLogMethod = exceptionLogMethod;
	}
	public User getUserId() {
		return userId;
	}
	public void setUserId(User userId) {
		this.userId = userId;
	}
}
