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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.Schedule;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IScheduleService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/schedule")
@CrossOrigin
public class ScheduleRestController {

	@Autowired
	private IScheduleService iScheduleService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@GetMapping("/findAllBySpecialistIdAndByBillMedicalTime/{specialistId}/{billMedicalTime}")
	public Map<String, Object> findAllBySpecialistIdAndByBillMedicalTime(HttpServletResponse response, @RequestHeader(value="Authorization") String token, @PathVariable("specialistId") long specialistId, @PathVariable("billMedicalTime") String billMedicalTime ){
		
		Map<String, Object> scheduleResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				scheduleResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				scheduleResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return scheduleResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if( Long.toString(specialistId) == null || Long.toString(specialistId) == "") {
						
						scheduleResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						scheduleResponse.put("message", "El ESPECIALISTA es requerido.");
						
						return scheduleResponse;
						
					}else if(billMedicalTime == null || billMedicalTime == ""){
						
						scheduleResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						scheduleResponse.put("message", "La FECHA MEDICA es requerida.");
						
						return scheduleResponse;
						
					}else {
						
						List<Schedule> scheduleList = iScheduleService.findAllScheduleBySpecialistIdAndByBillMedicalTimeAndStatusActive(specialistId, billMedicalTime);
						
						if(scheduleList.size() > 0) {
							
							scheduleResponse.put("schedule", scheduleList);
							scheduleResponse.put("statusCode", HttpStatus.OK.value());
							scheduleResponse.put("countRows", scheduleList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return scheduleResponse;
							
						}else {
							
							scheduleResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							scheduleResponse.put("countRows", scheduleList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return scheduleResponse;
						}
						
					}
					
										
				}catch(JwtException ex) {
					
					scheduleResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					scheduleResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return scheduleResponse;
					
				}
			}
			
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("ScheduleRestController");
			exceptionLogNew.setExceptionLogMethod("findAllBySpecialistIdAndByBillMedicalTime");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			scheduleResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			scheduleResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return scheduleResponse;
		}
	}
	
}
