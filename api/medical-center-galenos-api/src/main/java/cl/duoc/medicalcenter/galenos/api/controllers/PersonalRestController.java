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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.medicalcenter.galenos.api.models.entity.EventLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.Personal;
import cl.duoc.medicalcenter.galenos.api.models.entity.Profile;
import cl.duoc.medicalcenter.galenos.api.models.entity.User;
import cl.duoc.medicalcenter.galenos.api.models.service.IEventLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IPersonalService;
import cl.duoc.medicalcenter.galenos.api.models.service.IUserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/personal")
@CrossOrigin
public class PersonalRestController {

	@Autowired
	private IPersonalService iPersonalService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IEventLogService iEventLogService;
	
	@Autowired
	private IUserService iUserService;
	
	@GetMapping("/findAll/{page}")
	public Map<String, Object> findAll(HttpServletResponse response, @RequestHeader(value="Authorization") String token, @PathVariable("page") int page){
		
		Map<String, Object> personalResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				personalResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				personalResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return personalResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					int size = 5;
					
					List<Personal> personalList = iPersonalService.findAllPersonalByStatusActive(PageRequest.of(page, size));
					List<Personal> personalListCount = iPersonalService.findAllPersonalByStatusActiveCount();
					
					if(personalList.size() > 0) {
						
						personalResponse.put("personal", personalList);
						personalResponse.put("statusCode", HttpStatus.OK.value());
						personalResponse.put("countRows", personalListCount.size());
						personalResponse.put("currentPage", page);
						personalResponse.put("countRowsByPage", personalList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return personalResponse;
						
					}else {
						
						personalResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						personalResponse.put("countRows", personalList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return personalResponse;
					}
					
				}catch(JwtException ex) {
					
					personalResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					personalResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return personalResponse;
					
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("PersonalRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			personalResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			personalResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return personalResponse;
		}
	}
	
	@PostMapping("/create")
	public Map<String, Object> create(HttpServletResponse response, @RequestBody User user, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> userResponse = new HashMap<String, Object>();
		
		
		try{
			
				if(token == null || token == "") {
				
					userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
					userResponse.put("message", "El TOKEN es requerido.");
	
					response.setStatus(HttpServletResponse.SC_OK);
					return userResponse;
				
				}else {
				
					final String KEY_STRING = "medical-center-galenos-key";
					final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
					
					try{
						
						Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
						
						if(user.getUserPassword() == null || user.getUserPassword() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La CLAVE es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalRun() == null || user.getPersonalId().getPersonalRun() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El RUT es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getProfileId().getProfileId()) == null || Long.toString(user.getProfileId().getProfileId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PERFIL es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalFirstName() == null || user.getPersonalId().getPersonalFirstName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PRIMER NOMBRE es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalSecondName() == null || user.getPersonalId().getPersonalSecondName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El SEGUNDO NOMBRE es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalSurName() == null || user.getPersonalId().getPersonalSurName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PRIMER APELLIDO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalSecondSurName() == null || user.getPersonalId().getPersonalSecondSurName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El SEGUNDO APELLIDO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalBirthDate() == null || user.getPersonalId().getPersonalBirthDate().toString() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La FECHA DE NACIMIENTO es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalEmail() == null || user.getPersonalId().getPersonalEmail() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El CORREO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getPersonalId().getCategoryId().getCategoryId()) == null || Long.toString(user.getPersonalId().getCategoryId().getCategoryId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La CATEGORIA es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getPersonalId().getBranchOfficeId().getBranchOfficeId()) == null || Long.toString(user.getPersonalId().getBranchOfficeId().getBranchOfficeId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La SUCURSAL es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getPersonalId().getGenderId().getGenderId()) == null || Long.toString(user.getPersonalId().getGenderId().getGenderId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El GENERO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else {
							
							Personal personalExistByRun = iPersonalService.findByRun(user.getPersonalId().getPersonalRun());
							Personal personalExistByEmail = iPersonalService.findByEmail(user.getPersonalId().getPersonalEmail());
							
							
							if(personalExistByRun == null && personalExistByEmail == null) {
								
								String personalNewStatus = "1";
								String userNewStatus = "1";
								
								String personalFullNameNew = user.getPersonalId().getPersonalFirstName() + " " + user.getPersonalId().getPersonalSecondName() + " " + user.getPersonalId().getPersonalSurName() + " " + user.getPersonalId().getPersonalSecondSurName();
								
								user.getPersonalId().setPersonalStatus(personalNewStatus.charAt(0));
								user.getPersonalId().setPersonalFullName(personalFullNameNew);
								
								
								Personal personalNew = iPersonalService.save(user.getPersonalId());
								Profile profile = new Profile(user.getProfileId().getProfileId());
								
								user.setProfileId(profile);
								user.setPersonalId(personalNew);
								user.setUserStatus(userNewStatus.charAt(0));
								String password = passwordEncoder.encode(user.getUserPassword());
								user.setUserPassword(password);
								
								User userNew = iUserService.save(user);
								
								EventLog eventLogNew = new EventLog();
								
								Date dateNow = new Date();
								
								eventLogNew.setUserId(userNew);
								eventLogNew.setEventLogDate(dateNow);
								eventLogNew.setEventLogDescription("Personal created successfully.");
								eventLogNew.setEventLogStatusCode(HttpStatus.CREATED.value());
								
								iEventLogService.save(eventLogNew);
								
								userResponse.put("personalNew", personalNew);
								userResponse.put("userNew", userNew);
								userResponse.put("statusCode", HttpStatus.CREATED.value());

								response.setStatus(HttpServletResponse.SC_OK);
								return userResponse;
								
							}else {
								
								userResponse.put("statusCode", HttpStatus.OK.value());
								userResponse.put("message", "El PERSONAL ya existe.");

								response.setStatus(HttpServletResponse.SC_OK);
								return userResponse;
							}
						}
						
					}catch(JwtException ex) {
						
						userResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
						userResponse.put("message", "El TOKEN esta expirado.");

						response.setStatus(HttpServletResponse.SC_OK);
						return userResponse;
						
					}
					
				}
			
						
		}catch(Exception ex) {
			
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("PersonalRestController");
			exceptionLogNew.setExceptionLogMethod("create");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			userResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			userResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return userResponse;
		}
	}
	
	@PutMapping("/updatePersonalByPersonalId")
	public Map<String, Object> updatePersonalByPersonalId(HttpServletResponse response, @RequestBody User user, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> userResponse = new HashMap<String, Object>();
		
		
		try{
			
				if(token == null || token == "") {
				
					userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
					userResponse.put("message", "El TOKEN es requerido.");
	
					response.setStatus(HttpServletResponse.SC_OK);
					return userResponse;
				
				}else {
				
					final String KEY_STRING = "medical-center-galenos-key";
					final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
					
					try{
						
						Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
						
						if(user.getUserPassword() == null || user.getUserPassword() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La CLAVE es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getUserId()) == null || Long.toString(user.getUserId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El USUARIO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getPersonalId().getPersonalId()) == null || Long.toString(user.getPersonalId().getPersonalId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PERSONAL es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalRun() == null || user.getPersonalId().getPersonalRun() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El RUT es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalFirstName() == null || user.getPersonalId().getPersonalFirstName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PRIMER NOMBRE es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalSecondName() == null || user.getPersonalId().getPersonalSecondName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El SEGUNDO NOMBRE es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalSurName() == null || user.getPersonalId().getPersonalSurName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PRIMER APELLIDO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalSecondSurName() == null || user.getPersonalId().getPersonalSecondSurName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El SEGUNDO APELLIDO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalBirthDate() == null || user.getPersonalId().getPersonalBirthDate().toString() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La FECHA DE NACIMIENTO es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getPersonalId().getPersonalEmail() == null || user.getPersonalId().getPersonalEmail() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El CORREO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getPersonalId().getCategoryId().getCategoryId()) == null || Long.toString(user.getPersonalId().getCategoryId().getCategoryId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La CATEGORIA es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getPersonalId().getBranchOfficeId().getBranchOfficeId()) == null || Long.toString(user.getPersonalId().getBranchOfficeId().getBranchOfficeId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La SUCURSAL es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getPersonalId().getGenderId().getGenderId()) == null || Long.toString(user.getPersonalId().getGenderId().getGenderId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El GENERO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else {
							
							String personalFullName = user.getPersonalId().getPersonalFirstName() + " " + user.getPersonalId().getPersonalSecondName() + " " + user.getPersonalId().getPersonalSurName() + " " + user.getPersonalId().getPersonalSecondSurName();
							String password = passwordEncoder.encode(user.getUserPassword());
							
							int personalUpdate = iPersonalService.updatePersonalByPersonalId(user.getPersonalId().getPersonalRun(), user.getPersonalId().getPersonalFirstName(), user.getPersonalId().getPersonalSecondName(), user.getPersonalId().getPersonalSurName(), user.getPersonalId().getPersonalSecondSurName(), personalFullName, user.getPersonalId().getPersonalBirthDate(), user.getPersonalId().getPersonalEmail(), user.getPersonalId().getGenderId().getGenderId(), user.getPersonalId().getCategoryId().getCategoryId(), user.getPersonalId().getBranchOfficeId().getBranchOfficeId(), user.getPersonalId().getPersonalId()); 
							int userUpdate = iUserService.updateUserByUserId(password, user.getUserId());
							
							if(personalUpdate == 1 && userUpdate == 1) {
								
								EventLog eventLogNew = new EventLog();
								
								Date dateNow = new Date();
								
								eventLogNew.setUserId(user);
								eventLogNew.setEventLogDate(dateNow);
								eventLogNew.setEventLogDescription( "The personal was updated correctly");
								eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
								
								iEventLogService.save(eventLogNew);
								
								userResponse.put("statusCode", HttpStatus.OK.value());
								userResponse.put("message", "Se actualizo el PERSONAL correctamente.");
				
								response.setStatus(HttpServletResponse.SC_OK);
								return userResponse;
								
							}else {
								
								userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
								userResponse.put("message", "El PERSONAL no existe.");
								
								response.setStatus(HttpServletResponse.SC_OK);
								return userResponse;
								
							}
						}
						
					}catch(JwtException ex) {
						
						userResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
						userResponse.put("message", "El TOKEN esta expirado.");

						response.setStatus(HttpServletResponse.SC_OK);
						return userResponse;
						
					}
					
				}
			
						
		}catch(Exception ex) {
			
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setUserId(user);
			exceptionLogNew.setExceptionLogController("PersonalRestController");
			exceptionLogNew.setExceptionLogMethod("create");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			userResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			userResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return userResponse;
		}
	}
	
}
