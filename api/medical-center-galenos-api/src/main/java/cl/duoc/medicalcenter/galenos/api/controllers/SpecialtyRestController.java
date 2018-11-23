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
import cl.duoc.medicalcenter.galenos.api.models.entity.Specialty;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.ISpecialtyService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/specialty")
@CrossOrigin
public class SpecialtyRestController {

	@Autowired
	private ISpecialtyService iSpecialtyService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
		
	@GetMapping("/findByBranchOfficeId/{branchOfficeId}")
	public Map<String, Object> findByBranchOfficeId(HttpServletResponse response, @PathVariable("branchOfficeId") long branchOfficeId, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> specialtyResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				specialtyResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				specialtyResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return specialtyResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Long.toString(branchOfficeId) == null || Long.toString(branchOfficeId) == "") {
						
						specialtyResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						specialtyResponse.put("message", "La SUCURSAL es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return specialtyResponse;
						
					}else {
						
						List<Specialty> specialtyList = iSpecialtyService.findAllSpecialtyByBranchOfficeIdAndByStatusActive(branchOfficeId);
						
						if(specialtyList.size() > 0) {
							
							specialtyResponse.put("specialty", specialtyList);
							specialtyResponse.put("statusCode", HttpStatus.OK.value());
							specialtyResponse.put("countRows", specialtyList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return specialtyResponse;
							
						}else {
							
							specialtyResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							specialtyResponse.put("countRows", specialtyList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return specialtyResponse;
						}
						
					}
					
					
				}catch(JwtException ex) {
					
					specialtyResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					specialtyResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return specialtyResponse;
				}
			}
			
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("SpecialtyRestController");
			exceptionLogNew.setExceptionLogMethod("findByBranchOfficeId");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			specialtyResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			specialtyResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return specialtyResponse;
		}
	}
}
