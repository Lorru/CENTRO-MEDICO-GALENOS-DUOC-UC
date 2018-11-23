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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.medicalcenter.galenos.api.models.entity.EventLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.service.IEventLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/eventLog")
@CrossOrigin
public class EventLogRestController {

	@Autowired
	private IEventLogService iEventLogService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	
	@GetMapping("/findAll/{page}")
	public Map<String, Object> findAll(HttpServletResponse response, @PathVariable("page") int page, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> eventLogResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				eventLogResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				eventLogResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return eventLogResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Integer.toString(page) == null || Integer.toString(page) == "") {
						
						eventLogResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						eventLogResponse.put("message", "La PAGINA es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return eventLogResponse;
						
					}else {
						
						int size = 5;
						
						List<EventLog> eventLogList = iEventLogService.findAllEventLog(PageRequest.of(page, size));
						List<EventLog> eventLogListCount = iEventLogService.findAllEventLogCount();
						
						if(eventLogList.size() > 0) {
							
							eventLogResponse.put("eventLog", eventLogList);
							eventLogResponse.put("statusCode", HttpStatus.OK.value());
							eventLogResponse.put("countRows", eventLogListCount.size());
							eventLogResponse.put("currentPage", page);
							eventLogResponse.put("countRowsByPage", eventLogList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return eventLogResponse;
							
						}else {
							
							eventLogResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							eventLogResponse.put("countRows", eventLogList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return eventLogResponse;
						}
					}
					
				}catch(JwtException ex) {
				
					eventLogResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					eventLogResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return eventLogResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("EventLogRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			eventLogResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			eventLogResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return eventLogResponse;
		}
	}
}
