package cl.duoc.medicalcenter.galenos.api.controllers;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.medicalcenter.galenos.api.models.entity.Bill;
import cl.duoc.medicalcenter.galenos.api.models.entity.EventLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.Mail;
import cl.duoc.medicalcenter.galenos.api.models.entity.StateMedicalTime;
import cl.duoc.medicalcenter.galenos.api.models.entity.User;
import cl.duoc.medicalcenter.galenos.api.models.service.IBillService;
import cl.duoc.medicalcenter.galenos.api.models.service.IEventLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IMailService;
import cl.duoc.medicalcenter.galenos.api.models.service.IUserService;
import cl.duoc.medicalcenter.galenos.api.models.service.IVoucherService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/bill")
@CrossOrigin
public class BillRestController {

	@Autowired
	private IBillService iBillService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@Autowired
	private IEventLogService iEventLogService;
	
	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private IMailService iMailService;
	
	@Autowired IVoucherService iVoucherService;
			
	@PostMapping("/create")
	public Map<String, Object> create(HttpServletResponse response, @RequestBody Bill bill, @RequestHeader(value="Authorization") String token){
	
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(bill.getBillMedicalTime() == null || bill.getBillMedicalTime().toString() == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El LA FECHA DE RESERVA es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(Long.toString(bill.getPatientId().getPatientId()) == null || Long.toString(bill.getPatientId().getPatientId()) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El PACIENTE es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(Long.toString(bill.getSpecialistId().getSpecialistId()) == null || Long.toString(bill.getSpecialistId().getSpecialistId()) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El ESPECIALISTA es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						String billNewStatus = "1";
						
						StateMedicalTime stateMedicalTime = new StateMedicalTime(1);
						
						bill.setBillStatus(billNewStatus.charAt(0));
						bill.setStateMedicalTimeId(stateMedicalTime);

						Bill billNew = iBillService.save(bill);
												
						
						Bill billNewExist = iBillService.findByBillIdAndStatusActive(billNew.getBillId());
						
						EventLog eventLogNew = new EventLog();
						
						Date dateNow = new Date();
						
						User userExist = iUserService.findUserByPatientId(bill.getPatientId().getPatientId());
						
						Mail mail = new Mail();
						String subject = "Reserva de Hora Médica";
						
						mail.setTo(userExist.getPatientId().getPatientEmail());
						mail.setSubject(subject);
						mail.setMessage("Tu hora médica se reservo correctamente.");
						
						Map<String, Object> htmlVariables = new HashMap<String, Object>();
						
						htmlVariables.put("subject", subject);
						htmlVariables.put("billNew", billNewExist);
						
						mail.setHtmlVariables(htmlVariables);
						
						iMailService.sendMedicalTimeReservationTemplate(mail);
						
						eventLogNew.setUserId(userExist);
						eventLogNew.setEventLogDate(dateNow);
						eventLogNew.setEventLogDescription( "The reservation was created successfully");
						eventLogNew.setEventLogStatusCode(HttpStatus.CREATED.value());
						
						iEventLogService.save(eventLogNew);
						
						billResponse.put("statusCode", HttpStatus.CREATED.value());
						billResponse.put("billNew", billNewExist);

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}
					
				}catch(JwtException ex) {
					
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
					
				}
			}
			
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			User userExist = iUserService.findUserByPatientId(bill.getPatientId().getPatientId());
			
			exceptionLogNew.setUserId(userExist);
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("create");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
			
		}
	}
	
	@GetMapping("/findAllByStateMedicalTimeReservedAndStatusActive/{page}")
	public Map<String, Object> findAllByStateMedicalTimeReservedAndStatusActive(HttpServletResponse response, @PathVariable("page") int page, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Integer.toString(page) == null || Integer.toString(page) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La PAGINA es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						int size = 5;
						
						List<Bill> billList = iBillService.findAllByStateMedicalTimeReservedAndStatusActive(PageRequest.of(page, size));
						List<Bill> billListCount = iBillService.findAllByStateMedicalTimeReservedAndStatusActiveCount();
						
						if(billList.size() > 0) {
							
							billResponse.put("bill", billList);
							billResponse.put("statusCode", HttpStatus.OK.value());
							billResponse.put("countRows", billListCount.size());
							billResponse.put("currentPage", page);
							billResponse.put("countRowsByPage", billList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("countRows", billList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
						}
					}
					
				}catch(JwtException ex) {
				
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("findAllByStateMedicalTimeReservedAndStatusActive");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
		}
	}
	
	@GetMapping("/findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive/{patientRun}/{page}")
	public Map<String, Object> findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(HttpServletResponse response, @PathVariable("patientRun") String patientRun, @PathVariable("page") int page, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Integer.toString(page) == null || Integer.toString(page) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La PAGINA es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(patientRun == "undefined"){
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El RUT es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						int size = 5;
						
						List<Bill> billList = iBillService.findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive(patientRun, PageRequest.of(page, size));
						List<Bill> billListCount = iBillService.findAllByStateMedicalTimeReservedAndPatientRunAndStatusActiveCount(patientRun);
						
						if(billList.size() > 0) {
							
							billResponse.put("bill", billList);
							billResponse.put("statusCode", HttpStatus.OK.value());
							billResponse.put("countRows", billListCount.size());
							billResponse.put("countRowsByPage", billList.size());
							billResponse.put("currentPage", page);
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("countRows", billList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
						}
					}
					
				}catch(JwtException ex) {
				
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("findAllByStateMedicalTimeReservedAndPatientRunAndStatusActive");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
		}
	}
	
	@PutMapping("/updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary")
	public Map<String, Object> updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary(HttpServletResponse response, @RequestBody Bill bill, @RequestHeader(value="Authorization") String token){
	
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
				if(token == null || token == "") {
				
					billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
					billResponse.put("message", "El TOKEN es requerido.");
	
					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				
				}else {
				
					final String KEY_STRING = "medical-center-galenos-key";
					final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
					try {
						
						Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
						
						if(Long.toString(bill.getPaymentTypeId().getPaymentTypeId()) == null || Long.toString(bill.getPaymentTypeId().getPaymentTypeId()) == "") {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("message", "El MEDIO DE PAGO es requerido.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else if(Long.toString(bill.getForecastId().getForecastId()) == null || Long.toString(bill.getForecastId().getForecastId()) == "") {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("message", "La Previsión es requerida.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else if(Long.toString(bill.getBillId()) == null || Long.toString(bill.getBillId()) == "") {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("message", "La FACTURA es requerida.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else if(Integer.toString(bill.getBillCommissions()) == null || Integer.toString(bill.getBillCommissions()) == "") {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("message", "La COMISIÓN es requerida.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else if(Integer.toString(bill.getBillSalary()) == null || Integer.toString(bill.getBillSalary()) == "") {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("message", "El SALARIO es requerida.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							int billUpdate = iBillService.updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary(bill.getPaymentTypeId().getPaymentTypeId(), bill.getForecastId().getForecastId(), bill.getBillCommissions(), bill.getBillSalary(), bill.getBillId());
							
							if(billUpdate == 1) {
								
								EventLog eventLogNew = new EventLog();
								
								Date dateNow = new Date();
								
								Bill billExist = iBillService.findByBillIdAndStatusActive(bill.getBillId());
								
								User userExist = iUserService.findUserByPatientId(billExist.getPatientId().getPatientId());
								
								eventLogNew.setUserId(userExist);
								eventLogNew.setEventLogDate(dateNow);
								eventLogNew.setEventLogDescription( "The medical time was updated correctly");
								eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
								
								iEventLogService.save(eventLogNew);
								
								byte []  voucher = iVoucherService.billVoucher(billExist);
								
								String voucherName = billExist.getBillMedicalTime() + "_" + billExist.getPatientId().getPatientRun();
								
								
								billResponse.put("voucher", Base64.getEncoder().encodeToString(voucher));
								billResponse.put("extention", ".pdf");
								billResponse.put("voucherName", voucherName);
								billResponse.put("statusCode", HttpStatus.OK.value());
								billResponse.put("message", "Se registro el pago correctamente.");
				
								response.setStatus(HttpServletResponse.SC_OK);
								return billResponse;
								
							}else {
								
								billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
								billResponse.put("message", "La FACTURA no existe.");
								
								response.setStatus(HttpServletResponse.SC_OK);
								return billResponse;
								
							}
							
						}
						
					}catch(JwtException ex) {
						
						billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
						billResponse.put("message", "El TOKEN esta expirado.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}
				}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			Bill billExist = iBillService.findByBillIdAndStatusActive(bill.getBillId());
			
			User userExist = iUserService.findUserByPatientId(billExist.getPatientId().getPatientId());
			
			exceptionLogNew.setUserId(userExist);
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("updateBillByBillIdToStateMedicalTimeAndToForecastAndToPaymentTypeAndBillCommissionsAndBillSalary");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
			
		}
	}
	
	@GetMapping("/findAllByPatientRunAndStatusActive/{patientRun}/{page}")
	public Map<String, Object> findAllByPatientRunAndStatusActive(HttpServletResponse response, @PathVariable("patientRun") String patientRun, @PathVariable("page") int page, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Integer.toString(page) == null || Integer.toString(page) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La PAGINA es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(patientRun == "undefined"){
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El RUT es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						int size = 5;
						
						List<Bill> billList = iBillService.findAllByPatientRunAndStatusActive(patientRun, PageRequest.of(page, size));
						List<Bill> billListCount = iBillService.findAllByPatientRunAndStatusActiveCount(patientRun);
						
						if(billList.size() > 0) {
							
							billResponse.put("bill", billList);
							billResponse.put("statusCode", HttpStatus.OK.value());
							billResponse.put("countRows", billListCount.size());
							billResponse.put("countRowsByPage", billList.size());
							billResponse.put("currentPage", page);
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("countRows", billList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
						}
					}
					
				}catch(JwtException ex) {
				
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("findAllByPatientRunAndStatusActive");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
		}
	}
	
	@DeleteMapping("/updateBillByBillIdToBillStatusInactivo/{billId}")
	public Map<String, Object> updateBillByBillIdToBillStatusInactivo(HttpServletResponse response, @PathVariable("billId") long billId, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
				if(token == null || token == "") {
				
					billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
					billResponse.put("message", "El TOKEN es requerido.");
	
					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				
				}else {
				
					final String KEY_STRING = "medical-center-galenos-key";
					final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
					try {
						
						Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
						
						if(Long.toString(billId) == null || Long.toString(billId) == "") {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("message", "El MEDIO DE PAGO es requerido.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							Bill bill = iBillService.findByBillIdAndStatusActive(billId);
							
							User userExist = iUserService.findUserByPatientId(bill.getPatientId().getPatientId());
							
							int billUpdate = iBillService.updateBillByBillIdToBillStatusInactivo(billId);
							
							if(billUpdate == 1) {
								
								EventLog eventLogNew = new EventLog();
								
								Date dateNow = new Date();
								
								eventLogNew.setUserId(userExist);
								eventLogNew.setEventLogDate(dateNow);
								eventLogNew.setEventLogDescription( "The medical time was updated correctly");
								eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
								
								iEventLogService.save(eventLogNew);
								
								billResponse.put("statusCode", HttpStatus.OK.value());
								billResponse.put("message", "Se ANULO la hora médica correctamente.");
				
								response.setStatus(HttpServletResponse.SC_OK);
								return billResponse;
								
							}else {
								
								billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
								billResponse.put("message", "La FACTURA no existe.");
								
								response.setStatus(HttpServletResponse.SC_OK);
								return billResponse;
								
							}
							
						}
						
					}catch(JwtException ex) {
						
						billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
						billResponse.put("message", "El TOKEN esta expirado.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}
				}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			Bill bill = iBillService.findByBillIdAndStatusActive(billId);
			
			User userExist = iUserService.findUserByPatientId(bill.getPatientId().getPatientId());
			
			exceptionLogNew.setUserId(userExist);
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("updateBillByBillIdToBillStatusInactivo");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
			
		}
	}
	
	@GetMapping("/findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive/{specialistId}/{page}")
	public Map<String, Object> findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(HttpServletResponse response, @PathVariable("specialistId") long specialistId, @PathVariable("page") int page, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Integer.toString(page) == null || Integer.toString(page) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La PAGINA es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(Long.toString(specialistId) == "undefined"){
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El ESPECIALISTA es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						int size = 5;
						
						List<Bill> billList = iBillService.findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive(specialistId, PageRequest.of(page, size));
						List<Bill> billListCount = iBillService.findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActiveCount(specialistId);
						
						if(billList.size() > 0) {
							
							billResponse.put("bill", billList);
							billResponse.put("statusCode", HttpStatus.OK.value());
							billResponse.put("countRows", billListCount.size());
							billResponse.put("countRowsByPage", billList.size());
							billResponse.put("currentPage", page);
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("countRows", billList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
						}
					}
					
				}catch(JwtException ex) {
				
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("findAllBySpecialistIdAndStateMedicalTimeOnHoldAndStatusActive");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
		}
	}
	
	@GetMapping("/findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive/{specialistId}/{page}")
	public Map<String, Object> findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(HttpServletResponse response, @PathVariable("specialistId") long specialistId, @PathVariable("page") int page, @RequestHeader(value="Authorization")String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Integer.toString(page) == null || Integer.toString(page) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La PAGINA es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(Long.toString(specialistId) == "undefined"){
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El ESPECIALISTA es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						int size = 5;
						
						List<Bill> billList = iBillService.findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive(specialistId, PageRequest.of(page, size));
						List<Bill> billListCount = iBillService.findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActiveCount(specialistId);
						
						if(billList.size() > 0) {
							
							billResponse.put("bill", billList);
							billResponse.put("statusCode", HttpStatus.OK.value());
							billResponse.put("countRows", billListCount.size());
							billResponse.put("countRowsByPage", billList.size());
							billResponse.put("currentPage", page);
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("countRows", billList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
						}
					}
					
				}catch(JwtException ex) {
				
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("findAllBySpecialistIdAndStateMedicalTimeAttendedAndStatusActive");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
		}
	}
	
	@PutMapping("/updateBillByBillIdToStateMedicalTimeAttended")
	public Map<String, Object> updateBillByBillIdToStateMedicalTimeAttended(HttpServletResponse response, @RequestHeader(value="Authorization") String token, @RequestBody Bill bill){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
				if(token == null || token == "") {
				
					billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
					billResponse.put("message", "El TOKEN es requerido.");
	
					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				
				}else {
				
					final String KEY_STRING = "medical-center-galenos-key";
					final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
					try {
						
						Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
						
						if(Long.toString(bill.getBillId()) == null || Long.toString(bill.getBillId()) == "") {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("message", "La RESERVA es requerida.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							Bill billExist = iBillService.findByBillIdAndStatusActive(bill.getBillId());
							
							User userExist = iUserService.findUserByPatientId(billExist.getPatientId().getPatientId());
							
							int billUpdate = iBillService.updateBillByBillIdToStateMedicalTimeAttended(bill.getBillId());
							
							if(billUpdate == 1) {
								
								EventLog eventLogNew = new EventLog();
								
								Date dateNow = new Date();
								
								eventLogNew.setUserId(userExist);
								eventLogNew.setEventLogDate(dateNow);
								eventLogNew.setEventLogDescription( "The medical time was updated correctly");
								eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
								
								iEventLogService.save(eventLogNew);
								
								billResponse.put("statusCode", HttpStatus.OK.value());
								billResponse.put("message", "Se atendio el PACIENTE correctamente.");
				
								response.setStatus(HttpServletResponse.SC_OK);
								return billResponse;
								
							}else {
								
								billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
								billResponse.put("message", "La RESERVA no existe.");
								
								response.setStatus(HttpServletResponse.SC_OK);
								return billResponse;
								
							}
							
						}
						
					}catch(JwtException ex) {
						
						billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
						billResponse.put("message", "El TOKEN esta expirado.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}
				}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			Bill billExist = iBillService.findByBillIdAndStatusActive(bill.getBillId());
			
			User userExist = iUserService.findUserByPatientId(billExist.getPatientId().getPatientId());
			
			exceptionLogNew.setUserId(userExist);
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("updateBillByBillIdToStateMedicalTimeAttended");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
			
		}
	}
	
	@GetMapping("/findBySpecialistIdAndDateStartAndDateEndAndStatusActive/{specialistId}/{dateStart}/{dateEnd}")
	public Map<String, Object> findBySpecialistIdAndDateStartAndDateEndAndStatusActive(HttpServletResponse response, @PathVariable("specialistId") long specialistId, @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Long.toString(specialistId) == null || Long.toString(specialistId) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El ESPECIALISTA es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(dateStart == null || dateStart == "undefined") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La FECHA DE INICIO es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(dateEnd == null || dateEnd == "undefined") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La FECHA DE FINAL es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						List<String> reportBySpecialist = iBillService.findBySpecialistIdAndDateStartAndDateEndAndStatusActive(specialistId, dateStart, dateEnd);
						
						billResponse.put("statusCode", HttpStatus.OK.value());
						billResponse.put("reportBySpecialist", reportBySpecialist);
						
						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}
					
				}catch(JwtException ex) {
				
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("findBySpecialistIdAndDateStartAndDateEndAndStatusActive");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
		}
	}
	
	@GetMapping("/findByDateStartAndDateEndAndStatusActive/{dateStart}/{dateEnd}")
	public Map<String, Object> findByDateStartAndDateEndAndStatusActive(HttpServletResponse response, @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(dateStart == null || dateStart == "undefined") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La FECHA DE INICIO es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(dateEnd == null || dateEnd == "undefined") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La FECHA DE FINAL es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						List<String> reportAll = iBillService.findByDateStartAndDateEndAndStatusActive(dateStart, dateEnd);
						
						if(reportAll.size() > 0) {
							
							billResponse.put("statusCode", HttpStatus.OK.value());
							billResponse.put("reportAll", reportAll);
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}else {
							
							
							billResponse.put("statusCode", HttpStatus.OK.value());
							billResponse.put("reportAll", reportAll);
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
							
						}
						
						
					}
					
				}catch(JwtException ex) {
				
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("findByDateStartAndDateEndAndStatusActive");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
		}
	}
	
	@GetMapping("/findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport/{specialistId}/{dateStart}/{dateEnd}")
	public Map<String, Object> findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport(HttpServletResponse response, @PathVariable("specialistId") long specialistId, @PathVariable("dateStart") String dateStart, @PathVariable("dateEnd") String dateEnd, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> billResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				billResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return billResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Long.toString(specialistId) == null || Long.toString(specialistId) == "") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "El ESPECIALISTA es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(dateStart == null || dateStart == "undefined") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La FECHA DE INICIO es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else if(dateEnd == null || dateEnd == "undefined") {
						
						billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						billResponse.put("message", "La FECHA DE FINAL es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return billResponse;
						
					}else {
						
						List<Bill> reportBySpecialist = iBillService.findBySpecialistIdAndDateStartAndDateEndAndStatusActiveReport(specialistId, dateStart, dateEnd);
						
						if(reportBySpecialist.size() > 0) {
						
							
							byte [] voucher = iVoucherService.billReport(reportBySpecialist, dateStart, dateEnd);
							
							String voucherName = dateStart + "_" + dateEnd + "_" + reportBySpecialist.get(0).getSpecialistId().getSpecialistRun();
							
							billResponse.put("voucher", Base64.getEncoder().encodeToString(voucher));
							billResponse.put("extention", ".pdf");
							billResponse.put("voucherName", voucherName);
							billResponse.put("statusCode", HttpStatus.OK.value());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
						
						}else {
							
							billResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							billResponse.put("message", "El ESPECIALISTA no contiene reporte.");
							
							response.setStatus(HttpServletResponse.SC_OK);
							return billResponse;
						}
						
						
					}
					
				}catch(JwtException ex) {
				
					billResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					billResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return billResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BillRestController");
			exceptionLogNew.setExceptionLogMethod("findBySpecialistIdAndDateStartAndDateEndAndStatusActive");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			billResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			billResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return billResponse;
		}
	}
	
}
