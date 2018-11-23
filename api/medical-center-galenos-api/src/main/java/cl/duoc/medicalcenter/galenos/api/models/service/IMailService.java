package cl.duoc.medicalcenter.galenos.api.models.service;

import javax.mail.MessagingException;

import cl.duoc.medicalcenter.galenos.api.models.entity.Mail;

public interface IMailService {

	public void sendMedicalTimeReservationTemplate(Mail mail) throws MessagingException;
	
	public void sendMedicalTimeRecordationTemplate(Mail mail) throws MessagingException;
	
	public void sendMedicalTimeReservation(Mail mail);
}
