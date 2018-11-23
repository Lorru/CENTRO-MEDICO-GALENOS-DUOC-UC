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
@Table(name="FORECAST")
@Proxy(lazy = false)
public class Forecast implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="FORECAST_ID")
	private long forecastId;
	
	@Column(name="FORECAST_DESCRIPTION")
	private String forecastDescription;
	
	@Column(name="FORECAST_AMOUNT")
	private int forecastAmount;
	
	@Column(name="FORECAST_STATUS")
	private char forecastStatus;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "forecastId", fetch = FetchType.LAZY)
    private List<Bill> billList;
	
	public Forecast() {
		
	}
	
	public Forecast(long forecastId) {
		this.forecastId = forecastId;
	}
	public Forecast(long forecastId, String forecastDescription, int forecastAmount, char forecastStatus) {
		this.forecastId = forecastId;
		this.forecastDescription = forecastDescription;
		this.forecastAmount = forecastAmount;
		this.forecastStatus = forecastStatus;
	}
	public long getForecastId() {
		return forecastId;
	}
	public void setForecastId(long forecastId) {
		this.forecastId = forecastId;
	}
	public String getForecastDescription() {
		return forecastDescription;
	}
	public void setForecastDescription(String forecastDescription) {
		this.forecastDescription = forecastDescription;
	}
	
	public int getForecastAmount() {
		return forecastAmount;
	}

	public void setForecastAmount(int forecastAmount) {
		this.forecastAmount = forecastAmount;
	}

	public char getForecastStatus() {
		return forecastStatus;
	}
	public void setForecastStatus(char forecastStatus) {
		this.forecastStatus = forecastStatus;
	}

	public List<Bill> getBillList() {
		return billList;
	}

	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}
	
}
