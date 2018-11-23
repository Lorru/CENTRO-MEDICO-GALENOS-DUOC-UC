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

import cl.duoc.medicalcenter.galenos.api.models.entity.Category;
import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.service.ICategoryService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/category")
@CrossOrigin
public class CategoryRestController {

	@Autowired
	private ICategoryService iCategoryService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
		
	@GetMapping("/findByBranchOfficeId/{branchOfficeId}")
	public Map<String, Object> findByBranchOfficeId(HttpServletResponse response, @PathVariable("branchOfficeId") long branchOfficeId, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> categoryResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				categoryResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				categoryResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return categoryResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Long.toString(branchOfficeId) == null || Long.toString(branchOfficeId) == "") {
						
						categoryResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						categoryResponse.put("message", "La SUCURSAL es requerido.");

						response.setStatus(HttpServletResponse.SC_OK);
						return categoryResponse;
						
					}else {
						
						List<Category> categoryList = iCategoryService.findAllCategoryByBranchOfficeIdAndByStatusActive(branchOfficeId);
						
						if(categoryList.size() > 0) {
							
							categoryResponse.put("category", categoryList);
							categoryResponse.put("statusCode", HttpStatus.OK.value());
							categoryResponse.put("countRows", categoryList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return categoryResponse;
							
						}else {
							
							categoryResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							categoryResponse.put("countRows", categoryList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return categoryResponse;
						}
						
					}
					
					
				}catch(JwtException ex) {
					
					categoryResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					categoryResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return categoryResponse;
				}
			}
			
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("CategoryRestController");
			exceptionLogNew.setExceptionLogMethod("findByBranchOfficeId");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			categoryResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			categoryResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return categoryResponse;
		}
	}
	
}
