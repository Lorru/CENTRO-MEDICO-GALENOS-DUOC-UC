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

import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/exceptionLog")
@CrossOrigin
public class ExceptionLogRestController {

	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@GetMapping("/findAll/{page}")
	public Map<String, Object> findAll(HttpServletResponse response, @PathVariable("page") int page, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> exceptionLogResponse = new HashMap<String, Object>();
		
		try {
			
			if(token == null || token == "") {
				
				exceptionLogResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				exceptionLogResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return exceptionLogResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Integer.toString(page) == null || Integer.toString(page) == "") {
						
						exceptionLogResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						exceptionLogResponse.put("message", "La PAGINA es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return exceptionLogResponse;
						
					}else {
						
						int size = 5;
						
						List<ExceptionLog> exceptionLogList = iExceptionLogService.findAllExceptionLog(PageRequest.of(page, size));
						List<ExceptionLog> exceptionLogListCount = iExceptionLogService.findAllExceptionLogCount();
						
						if(exceptionLogList.size() > 0) {
							
							exceptionLogResponse.put("exceptionLog", exceptionLogList);
							exceptionLogResponse.put("statusCode", HttpStatus.OK.value());
							exceptionLogResponse.put("countRows", exceptionLogListCount.size());
							exceptionLogResponse.put("currentPage", page);
							exceptionLogResponse.put("countRowsByPage", exceptionLogList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return exceptionLogResponse;
							
						}else {
							
							exceptionLogResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							exceptionLogResponse.put("countRows", exceptionLogList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return exceptionLogResponse;
						}
					}
					
				}catch(JwtException ex) {
				
					exceptionLogResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					exceptionLogResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return exceptionLogResponse;
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("ExceptionLogRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			exceptionLogResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			exceptionLogResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return exceptionLogResponse;
		}
	}
}
