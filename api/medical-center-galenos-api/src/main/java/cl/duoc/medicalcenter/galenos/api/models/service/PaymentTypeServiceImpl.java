package cl.duoc.medicalcenter.galenos.api.models.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.medicalcenter.galenos.api.models.dao.IPaymentTypeDao;
import cl.duoc.medicalcenter.galenos.api.models.entity.PaymentType;

@Service
public class PaymentTypeServiceImpl implements IPaymentTypeService {

	@Autowired
	private IPaymentTypeDao iPaymentTypeDao;
	
	@Override
	@Transactional
	public List<PaymentType> findAll() {
		
		return iPaymentTypeDao.findAll();
	}

	@Override
	@Transactional
	public List<PaymentType> findAllPaymentTypeByStatusActive() {
		return iPaymentTypeDao.findAllPaymentTypeByStatusActive();
	}
}
