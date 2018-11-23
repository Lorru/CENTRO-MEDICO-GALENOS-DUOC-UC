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
import cl.duoc.medicalcenter.galenos.api.models.entity.Forecast;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IForecastService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/forecast")
@CrossOrigin
public class ForecastRestController {

	@Autowired
	private IForecastService iForecastService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@GetMapping("/findAll")
	public Map<String, Object> findAll(HttpServletResponse response, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> forecastResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				forecastResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				forecastResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return forecastResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					List<Forecast> forecastList = iForecastService.findAllForecastByStatusActive();
					
					if(forecastList.size() > 0) {
						
						forecastResponse.put("forecast", forecastList);
						forecastResponse.put("statusCode", HttpStatus.OK.value());
						forecastResponse.put("countRows", forecastList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return forecastResponse;
						
					}else {
						
						forecastResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						forecastResponse.put("countRows", forecastList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return forecastResponse;
					}
					
				}catch(JwtException ex) {
					
					forecastResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					forecastResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return forecastResponse;
				}
			}
			

			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("ForecastRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			forecastResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			forecastResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return forecastResponse;
		}
	}
}
