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
@Table(name="PAYMENT_TYPE")
@Proxy(lazy = false)
public class PaymentType implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="PAYMENT_TYPE_ID")
	private long paymentTypeId;
	
	@Column(name="PAYMENT_TYPE_DESCRIPTION")
	private String paymentTypeDescription;
	
	@Column(name="PAYMENT_TYPE_AMOUNT")
	private int paymentTypeAmount;
	
	@Column(name="PAYMENT_TYPE_STATUS")
	private char paymentTypeStatus;
	
	@JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "paymentTypeId", fetch = FetchType.LAZY)
    private List<Bill> billList;
	
	public PaymentType() {
		
	}
	public PaymentType(long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public PaymentType(long paymentTypeId, String paymentTypeDescription, int paymentTypeAmount, char paymentTypeStatus) {
		this.paymentTypeId = paymentTypeId;
		this.paymentTypeDescription = paymentTypeDescription;
		this.paymentTypeAmount = paymentTypeAmount;
		this.paymentTypeStatus = paymentTypeStatus;
	}
	public long getPaymentTypeId() {
		return paymentTypeId;
	}
	public void setPaymentTypeId(long paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	public String getPaymentTypeDescription() {
		return paymentTypeDescription;
	}
	public void setPaymentTypeDescription(String paymentTypeDescription) {
		this.paymentTypeDescription = paymentTypeDescription;
	}
	
	public int getPaymentTypeAmount() {
		return paymentTypeAmount;
	}
	public void setPaymentTypeAmount(int paymentTypeAmount) {
		this.paymentTypeAmount = paymentTypeAmount;
	}
	public char getPaymentTypeStatus() {
		return paymentTypeStatus;
	}
	public void setPaymentTypeStatus(char paymentTypeStatus) {
		this.paymentTypeStatus = paymentTypeStatus;
	}
	public List<Bill> getBillList() {
		return billList;
	}
	public void setBillList(List<Bill> billList) {
		this.billList = billList;
	}
	
}
