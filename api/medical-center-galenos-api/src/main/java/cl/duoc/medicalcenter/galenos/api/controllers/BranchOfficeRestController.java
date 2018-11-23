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

import cl.duoc.medicalcenter.galenos.api.models.entity.BranchOffice;
import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.service.IBranchOfficeService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;


@RestController
@RequestMapping("/api/branchOffice")
@CrossOrigin
public class BranchOfficeRestController {

	@Autowired
	private IBranchOfficeService iBranchOfficeService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@GetMapping("/findAll")
	public Map<String, Object> findAll(HttpServletResponse response, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> branchOfficeResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				branchOfficeResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				branchOfficeResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return branchOfficeResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					List<BranchOffice> branchOfficeList = iBranchOfficeService.findAllBranchOfficeByStatusActive();
					
					if(branchOfficeList.size() > 0) {
					
						branchOfficeResponse.put("branchOffice", branchOfficeList);
						branchOfficeResponse.put("statusCode", HttpStatus.OK.value());
						branchOfficeResponse.put("countRows", branchOfficeList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return branchOfficeResponse;
						
					}else {
						
						branchOfficeResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						branchOfficeResponse.put("countRows", branchOfficeList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return branchOfficeResponse;
					}
					
				}catch(JwtException ex) {
				
					branchOfficeResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					branchOfficeResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return branchOfficeResponse;
				}
				
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("BranchOfficeRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			branchOfficeResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			branchOfficeResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return branchOfficeResponse;
		}
	}
}
