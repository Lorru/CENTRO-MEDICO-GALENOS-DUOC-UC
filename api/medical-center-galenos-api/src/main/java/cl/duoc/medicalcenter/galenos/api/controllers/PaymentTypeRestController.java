package cl.duoc.medicalcenter.galenos.api.controllers;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.PaymentType;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IPaymentTypeService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/paymentType")
@CrossOrigin
public class PaymentTypeRestController {

	@Autowired
	private IPaymentTypeService iPaymentService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@GetMapping("/findAll")
	public Map<String, Object> findAll(HttpServletResponse response, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> paymentTypeResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				paymentTypeResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				paymentTypeResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return paymentTypeResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					List<PaymentType> paymentTypeList = iPaymentService.findAllPaymentTypeByStatusActive();
					
					if(paymentTypeList.size() > 0) {
						
						paymentTypeResponse.put("paymentType", paymentTypeList);
						paymentTypeResponse.put("statusCode", HttpStatus.OK.value());
						paymentTypeResponse.put("countRows", paymentTypeList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return paymentTypeResponse;
						
					}else {
						
						paymentTypeResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						paymentTypeResponse.put("countRows", paymentTypeList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return paymentTypeResponse;
					}
					
				}catch(JwtException ex) {
					
					paymentTypeResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					paymentTypeResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return paymentTypeResponse;
				}
			}
			
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("PaymentTypeRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			paymentTypeResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			paymentTypeResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return paymentTypeResponse;
		}
	}
}
