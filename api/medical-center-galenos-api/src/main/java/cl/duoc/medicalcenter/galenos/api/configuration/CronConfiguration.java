package cl.duoc.medicalcenter.galenos.api.configuration;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import cl.duoc.medicalcenter.galenos.api.models.entity.Bill;
import cl.duoc.medicalcenter.galenos.api.models.entity.EventLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.Mail;
import cl.duoc.medicalcenter.galenos.api.models.entity.User;
import cl.duoc.medicalcenter.galenos.api.models.service.IBillService;
import cl.duoc.medicalcenter.galenos.api.models.service.IEventLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IMailService;
import cl.duoc.medicalcenter.galenos.api.models.service.IUserService;

@Configuration
@EnableScheduling
public class CronConfiguration {

	@Autowired
	private IMailService iMailService;
	
	@Autowired
	private IBillService iBillService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@Autowired
	private IEventLogService iEventLogService;
	
	@Autowired
	private IUserService iUserService;
	
	@Scheduled(cron = "0 0 8 * * ?")
	public void sendMailRecordation() {
		
		try {
			
			List<Bill> billList = iBillService.findAllByStateMedicalTimeReservedRecordationAndStatusActive();
			List<Bill> billListRecordation = new ArrayList<Bill>();
			
			Date dateNow = new Date();
			
			for (Bill bill : billList) {
				
				Date billMedicalTime = Date.from(bill.getBillMedicalTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
				
				int day=(int) ((dateNow.getTime() - billMedicalTime.getTime())/86400000);
				
				if(day == 1) {
					
					billListRecordation.add(bill);
					
				}
			}
			
			if(billListRecordation.size() > 0) {
				
				Mail mail = new Mail();
				
				for (Bill bill : billListRecordation) {
					
					String subject = "Recordatio de Hora Médica";
					mail.setTo(bill.getPatientId().getPatientEmail());
					mail.setSubject(subject);
					mail.setMessage("Recordatio de Hora Médica.");
					
					Bill BillExist = iBillService.findByBillIdAndStatusActive(bill.getBillId());
					
					Map<String, Object> htmlVariables = new HashMap<String, Object>();
					
					htmlVariables.put("subject", subject);
					htmlVariables.put("billExist", BillExist);
					
					mail.setHtmlVariables(htmlVariables);
					
					User userExist = iUserService.findUserByPatientId(bill.getPatientId().getPatientId());
					
					EventLog eventLogNew = new EventLog();
					eventLogNew.setUserId(userExist);
					eventLogNew.setEventLogDate(dateNow);
					eventLogNew.setEventLogDescription( "Reminder email sent successfully");
					eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
					
					iEventLogService.save(eventLogNew);
					
					iMailService.sendMedicalTimeRecordationTemplate(mail);
				}
				
			}
			
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			exceptionLogNew.setExceptionLogController("CronConfiguration");
			exceptionLogNew.setExceptionLogMethod("sendMailRecordation");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
		}
		
	}
	
	@Scheduled(cron = "0 0 8 * * ?")
	public void updateBillByBillIdToBillStatusInactivo() {
		
		try {
			
			List<Bill> billList = iBillService.findAllByStateMedicalTimeReservedRecordationAndStatusActive();
			List<Bill> billListStatusInactivo = new ArrayList<Bill>();
			
			Date dateNow = new Date();
			
			for (Bill bill : billList) {
				
				Date billMedicalTime = Date.from(bill.getBillMedicalTime().atStartOfDay(ZoneId.systemDefault()).toInstant());
				
				int day=(int) ((dateNow.getTime() - billMedicalTime.getTime())/86400000);
				
				if(day > 1) {
					
					billListStatusInactivo.add(bill);
					
				}
			}
			
			if(billListStatusInactivo.size() > 0) {
				
				for(Bill bill : billListStatusInactivo) {
					
					int billUpdate = iBillService.updateBillByBillIdToBillStatusInactivo(bill.getBillId());
					
					if(billUpdate == 1) {
						
						User userExist = iUserService.findUserByPatientId(bill.getPatientId().getPatientId());
						
						EventLog eventLogNew = new EventLog();
						eventLogNew.setUserId(userExist);
						eventLogNew.setEventLogDate(dateNow);
						eventLogNew.setEventLogDescription( "The medical time was updated correctly");
						eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
						
						iEventLogService.save(eventLogNew);
						
					}
				}
				
			}
			
			
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			exceptionLogNew.setExceptionLogController("CronConfiguration");
			exceptionLogNew.setExceptionLogMethod("updateBillByBillIdToBillStatusInactivo");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
		}
		
	}
}
