package cl.duoc.medicalcenter.galenos.api.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import cl.duoc.medicalcenter.galenos.api.models.entity.PaymentType;

public interface IPaymentTypeDao extends JpaRepository<PaymentType, Long> {
	
	@Query("SELECT p FROM PaymentType p WHERE p.paymentTypeStatus = 1")
	public List<PaymentType> findAllPaymentTypeByStatusActive();
}
