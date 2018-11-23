package cl.duoc.medicalcenter.galenos.api.controllers;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.Gender;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IGenderService;


@RestController
@RequestMapping("/api/gender")
@CrossOrigin
public class GenderRestController {

	@Autowired
	private IGenderService iGenderService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@GetMapping("/findAll")
	public Map<String, Object> findAll(HttpServletResponse response){
		
		Map<String, Object> genderResponse = new HashMap<String, Object>();
		
		try{
			
			List<Gender> genderList = iGenderService.findAllGenderByStatusActive();
			
			if(genderList.size() > 0) {
				
				genderResponse.put("gender", genderList);
				genderResponse.put("statusCode", HttpStatus.OK.value());
				genderResponse.put("countRows", genderList.size());
				
				response.setStatus(HttpServletResponse.SC_OK);
				return genderResponse;
				
			}else {
				
				genderResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				genderResponse.put("countRows", genderList.size());
				
				response.setStatus(HttpServletResponse.SC_OK);
				return genderResponse;
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("GenderRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			genderResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			genderResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return genderResponse;
		}
	}
	
	
}
