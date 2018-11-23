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
import cl.duoc.medicalcenter.galenos.api.models.entity.Profile;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IProfileService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/profile")
@CrossOrigin
public class ProfileRestController {

	@Autowired
	private IProfileService iProfileService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@GetMapping("/findAll")
	public Map<String, Object> findAll(HttpServletResponse response, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> profileResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				profileResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				profileResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return profileResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					List<Profile> profileList = iProfileService.findAllProfileByStatusActive();
					
					if(profileList.size() > 0) {
						
						profileResponse.put("profile", profileList);
						profileResponse.put("statusCode", HttpStatus.OK.value());
						profileResponse.put("countRows", profileList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return profileResponse;
						
					}else {
						
						profileResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						profileResponse.put("countRows", profileList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return profileResponse;
					}
					
				}catch(JwtException ex) {
					
					profileResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					profileResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return profileResponse;
					
				}
				
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("ProfileRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			profileResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			profileResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return profileResponse;
		}
	}
}
