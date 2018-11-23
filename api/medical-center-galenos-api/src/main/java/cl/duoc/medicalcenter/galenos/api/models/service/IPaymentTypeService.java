package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import cl.duoc.medicalcenter.galenos.api.models.entity.PaymentType;

public interface IPaymentTypeService {

	public List<PaymentType> findAll();
	
	public List<PaymentType> findAllPaymentTypeByStatusActive();
}
