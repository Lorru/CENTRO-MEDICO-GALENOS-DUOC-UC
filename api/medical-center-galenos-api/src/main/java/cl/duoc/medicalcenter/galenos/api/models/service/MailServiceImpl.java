package cl.duoc.medicalcenter.galenos.api.models.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import cl.duoc.medicalcenter.galenos.api.models.entity.Mail;

@Service
public class MailServiceImpl implements IMailService {

	@Autowired
	private JavaMailSender iJavaMailSender;
	
	
	@Autowired
    private SpringTemplateEngine templateEngine;
    
	
	@Override
	public void sendMedicalTimeReservationTemplate(Mail mail) throws MessagingException {
		
	    MimeMessage mimeMessage = iJavaMailSender.createMimeMessage();
	    
	    final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	    
	    mimeMessageHelper.setSubject(mail.getSubject());
	    mimeMessageHelper.setTo(mail.getTo());
	    
	    Context context = new Context();
	    context.setVariables(mail.getHtmlVariables());
	    
	    String htmlContent = templateEngine.process("medical-time-reservation.html", context);
	    
	    mimeMessageHelper.setText(htmlContent, true);
	    mimeMessageHelper.addInline("logo.jpg", new ClassPathResource("static/img/login-register.jpg"));

	    iJavaMailSender.send(mimeMessage);
	    
	}

	@Override
	public void sendMedicalTimeReservation(Mail mail) {
		
		SimpleMailMessage simpleMailMessage = new SimpleMailMessage(); 
		
		simpleMailMessage.setTo(mail.getTo()); 
		simpleMailMessage.setSubject(mail.getSubject()); 
		simpleMailMessage.setText(mail.getMessage());
		
		iJavaMailSender.send(simpleMailMessage);
		
	}

	@Override
	public void sendMedicalTimeRecordationTemplate(Mail mail) throws MessagingException {
		
	    MimeMessage mimeMessage = iJavaMailSender.createMimeMessage();
	    
	    final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
	    
	    mimeMessageHelper.setSubject(mail.getSubject());
	    mimeMessageHelper.setTo(mail.getTo());
	    
	    Context context = new Context();
	    context.setVariables(mail.getHtmlVariables());
	    
	    String htmlContent = templateEngine.process("medical-time-recordation.html", context);
	    
	    mimeMessageHelper.setText(htmlContent, true);
	    mimeMessageHelper.addInline("logo.jpg", new ClassPathResource("static/img/login-register.jpg"));

	    iJavaMailSender.send(mimeMessage);
		
	}

}
