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
import cl.duoc.medicalcenter.galenos.api.models.entity.Profile;
import cl.duoc.medicalcenter.galenos.api.models.entity.Specialist;
import cl.duoc.medicalcenter.galenos.api.models.entity.User;
import cl.duoc.medicalcenter.galenos.api.models.service.IEventLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.ISpecialistService;
import cl.duoc.medicalcenter.galenos.api.models.service.IUserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/specialist")
@CrossOrigin
public class SpecialistRestController {

	@Autowired
	private ISpecialistService iSpecialistService;
	
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
		
		Map<String, Object> specialistResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				specialistResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return specialistResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					int size = 5;
					
					List<Specialist> specialistList = iSpecialistService.findAllSpecialist(PageRequest.of(page, size));
					List<Specialist> specialistListCount = iSpecialistService.findAllSpecialistCount();
					
					if(specialistList.size() > 0) {
						
						specialistResponse.put("specialist", specialistList);
						specialistResponse.put("statusCode", HttpStatus.OK.value());
						specialistResponse.put("countRows", specialistListCount.size());
						specialistResponse.put("currentPage", page);
						specialistResponse.put("countRowsByPage", specialistList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return specialistResponse;
						
					}else {
						
						specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						specialistResponse.put("countRows", specialistList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return specialistResponse;
					}
					
				}catch(JwtException ex) {
					
					specialistResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					specialistResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return specialistResponse;
					
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("SpecialistRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			specialistResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			specialistResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return specialistResponse;
		}
	}
	
	@GetMapping("/findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive/{specialtyId}/{branchOfficeId}")
	public Map<String, Object> findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(HttpServletResponse response, @PathVariable("specialtyId") long specialtyId, @PathVariable("branchOfficeId") long branchOfficeId, @RequestHeader(value="Authorization") String token ){
		
		Map<String, Object> specialistResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				specialistResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return specialistResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try{
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					if(Long.toString(specialtyId) == null || Long.toString(specialtyId) == "") {
						
						specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						specialistResponse.put("message", "La ESPECIALIDAD es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return specialistResponse;
						
					}else if(Long.toString(branchOfficeId) == null || Long.toString(branchOfficeId) == "") {
						
						specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						specialistResponse.put("message", "La SUCURSAL es requerida.");

						response.setStatus(HttpServletResponse.SC_OK);
						return specialistResponse;
						
					}else {
						
						List<Specialist> specialistList = iSpecialistService.findAllSpecialistBySpecialtyIdAndByBranchOfficeIdByStatusActive(specialtyId, branchOfficeId);
						
						if(specialistList.size() > 0) {
							
							specialistResponse.put("specialist", specialistList);
							specialistResponse.put("statusCode", HttpStatus.OK.value());
							specialistResponse.put("countRows", specialistList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return specialistResponse;
							
						}else {
							
							specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							specialistResponse.put("countRows", specialistList.size());
							
							response.setStatus(HttpServletResponse.SC_OK);
							return specialistResponse;
						}
						
					}
					
					
					
				}catch(JwtException ex) {
					
					specialistResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					specialistResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return specialistResponse;
					
				}
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("SpecialistRestController");
			exceptionLogNew.setExceptionLogMethod("findBySpecialtyId");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			specialistResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			specialistResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return specialistResponse;
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
							
						}else if(user.getSpecialistId().getSpecialistRun() == null || user.getSpecialistId().getSpecialistRun() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El RUT es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistFirstName() == null || user.getSpecialistId().getSpecialistFirstName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PRIMER NOMBRE es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistSecondName() == null || user.getSpecialistId().getSpecialistSecondName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El SEGUNDO NOMBRE es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistSurName() == null || user.getSpecialistId().getSpecialistSurName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PRIMER APELLIDO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistSecondSurName() == null || user.getSpecialistId().getSpecialistSecondSurName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El SEGUNDO APELLIDO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistBirthDate() == null || user.getSpecialistId().getSpecialistBirthDate().toString() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La FECHA DE NACIMIENTO es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistEmail() == null || user.getSpecialistId().getSpecialistEmail() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El CORREO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getSpecialistId().getSpecialtyId().getSpecialtyId()) == null || Long.toString(user.getSpecialistId().getSpecialtyId().getSpecialtyId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La ESPECIALIDAD es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getSpecialistId().getBranchOfficeId().getBranchOfficeId()) == null || Long.toString(user.getSpecialistId().getBranchOfficeId().getBranchOfficeId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La SUCURSAL es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getSpecialistId().getGenderId().getGenderId()) == null || Long.toString(user.getSpecialistId().getGenderId().getGenderId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El GENERO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else {
							
							Specialist specialistExistByRun = iSpecialistService.findByRun(user.getSpecialistId().getSpecialistRun());
							Specialist specialistExistByEmail = iSpecialistService.findByEmail(user.getSpecialistId().getSpecialistRun());
							
							
							if(specialistExistByRun == null && specialistExistByEmail == null) {
								
								String specialistNewStatus = "1";
								String userNewStatus = "1";
								
								String specialistFullNameNew = user.getSpecialistId().getSpecialistFirstName() + " " + user.getSpecialistId().getSpecialistSecondName() + " " + user.getSpecialistId().getSpecialistSurName() + " " + user.getSpecialistId().getSpecialistSecondSurName();
								
								user.getSpecialistId().setSpecialistStatus(specialistNewStatus.charAt(0));
								user.getSpecialistId().setSpecialistFullName(specialistFullNameNew);
								
								
								Specialist specialistNew = iSpecialistService.save(user.getSpecialistId());
								Profile profile = new Profile(2);
								
								user.setProfileId(profile);
								user.setSpecialistId(specialistNew);
								user.setUserStatus(userNewStatus.charAt(0));
								String password = passwordEncoder.encode(user.getUserPassword());
								user.setUserPassword(password);
								
								User userNew = iUserService.save(user);
								
								EventLog eventLogNew = new EventLog();
								
								Date dateNow = new Date();
								
								eventLogNew.setUserId(userNew);
								eventLogNew.setEventLogDate(dateNow);
								eventLogNew.setEventLogDescription("Specialist created successfully.");
								eventLogNew.setEventLogStatusCode(HttpStatus.CREATED.value());
								
								iEventLogService.save(eventLogNew);
								
								userResponse.put("specialistNew", specialistNew);
								userResponse.put("userNew", userNew);
								userResponse.put("statusCode", HttpStatus.CREATED.value());

								response.setStatus(HttpServletResponse.SC_OK);
								return userResponse;
								
							}else {
								
								userResponse.put("statusCode", HttpStatus.OK.value());
								userResponse.put("message", "El ESPECIALISTA ya existe.");

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
			
			exceptionLogNew.setExceptionLogController("SpecialistRestController");
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
	
	@PutMapping("/updateSpecialistBySpecialistIdAndSpecialistStatus")
	public Map<String, Object> updateSpecialistBySpecialistIdAndSpecialistStatus(HttpServletResponse response, @RequestBody Specialist specialist, @RequestHeader("Authorization") String token){
		
		Map<String, Object> specialistResponse = new HashMap<String, Object>();
		
		
		try{
			
				if(token == null || token == "") {
				
					specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
					specialistResponse.put("message", "El TOKEN es requerido.");
	
					response.setStatus(HttpServletResponse.SC_OK);
					return specialistResponse;
				
				}else {
				
					final String KEY_STRING = "medical-center-galenos-key";
					final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
					
					try{
						
						Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
						
						if(Long.toString(specialist.getSpecialistId()) == null || Long.toString(specialist.getSpecialistId()) == "") {
							
							specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							specialistResponse.put("message", "El ESPECIALISTA es requerido.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return specialistResponse;
							
						}else if(String.valueOf(specialist.getSpecialistStatus()) == null || String.valueOf(specialist.getSpecialistStatus()) == "") {
						
							specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							specialistResponse.put("message", "El STATUS es requerido.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return specialistResponse;
							
						}else {
							
							int specialistUpdate = iSpecialistService.updateSpecialistBySpecialistIdAndSpecialistStatus(specialist.getSpecialistId(), specialist.getSpecialistStatus());
							
							if(specialistUpdate == 1) {
								
								specialistResponse.put("statusCode", HttpStatus.OK.value());
								specialistResponse.put("message", "Se actualizo el especialista correctamente.");
				
								response.setStatus(HttpServletResponse.SC_OK);
								return specialistResponse;
								
							}else {
								
								specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
								specialistResponse.put("message", "El ESPECIALISTA no existe.");
								
								response.setStatus(HttpServletResponse.SC_OK);
								return specialistResponse;
								
							}
							
						}
						
					}catch(JwtException ex) {
						
						specialistResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
						specialistResponse.put("message", "El TOKEN esta expirado.");

						response.setStatus(HttpServletResponse.SC_OK);
						return specialistResponse;
						
					}
					
				}
		
		}catch(Exception ex) {
			
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("SpecialistRestController");
			exceptionLogNew.setExceptionLogMethod("updateSpecialistBySpecialistIdAndSpecialistStatus");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			specialistResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			specialistResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return specialistResponse;
		}
		
	}
	
	@PutMapping("/updateSpecialistBySpecialistId")
	public Map<String, Object> updateSpecialistBySpecialistId(HttpServletResponse response, @RequestBody User user, @RequestHeader(value="Authorization") String token){
		
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
							
						}else if(Long.toString(user.getSpecialistId().getSpecialistId()) == null || Long.toString(user.getSpecialistId().getSpecialistId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El ESPECIALISTA es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistRun() == null || user.getSpecialistId().getSpecialistRun() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El RUT es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistFirstName() == null || user.getSpecialistId().getSpecialistFirstName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PRIMER NOMBRE es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistSecondName() == null || user.getSpecialistId().getSpecialistSecondName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El SEGUNDO NOMBRE es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistSurName() == null || user.getSpecialistId().getSpecialistSurName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El PRIMER APELLIDO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistSecondSurName() == null || user.getSpecialistId().getSpecialistSecondSurName() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El SEGUNDO APELLIDO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistBirthDate() == null || user.getSpecialistId().getSpecialistBirthDate().toString() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La FECHA DE NACIMIENTO es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(user.getSpecialistId().getSpecialistEmail() == null || user.getSpecialistId().getSpecialistEmail() == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El CORREO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getSpecialistId().getSpecialtyId().getSpecialtyId()) == null || Long.toString(user.getSpecialistId().getSpecialtyId().getSpecialtyId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La ESPECIALIDAD es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getSpecialistId().getBranchOfficeId().getBranchOfficeId()) == null || Long.toString(user.getSpecialistId().getBranchOfficeId().getBranchOfficeId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "La SUCURSAL es requerida.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(Long.toString(user.getSpecialistId().getGenderId().getGenderId()) == null || Long.toString(user.getSpecialistId().getGenderId().getGenderId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El GENERO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else {
						
							String specialistFullName = user.getSpecialistId().getSpecialistFirstName() + " " + user.getSpecialistId().getSpecialistSecondName() + " " + user.getSpecialistId().getSpecialistSurName() + " " + user.getSpecialistId().getSpecialistSecondSurName();
							String password = passwordEncoder.encode(user.getUserPassword());
							
							int specialistUpdate = iSpecialistService.updateSpecialistBySpecialistId(user.getSpecialistId().getSpecialistRun(), user.getSpecialistId().getSpecialistFirstName(), user.getSpecialistId().getSpecialistSecondName(), user.getSpecialistId().getSpecialistSurName(), user.getSpecialistId().getSpecialistSecondSurName(), specialistFullName, user.getSpecialistId().getSpecialistBirthDate(), user.getSpecialistId().getSpecialistEmail(), user.getSpecialistId().getGenderId().getGenderId(), user.getSpecialistId().getSpecialtyId().getSpecialtyId(), user.getSpecialistId().getBranchOfficeId().getBranchOfficeId(), user.getSpecialistId().getSpecialistId());
							int userUpdate = iUserService.updateUserByUserId(password, user.getUserId());
							
							if(specialistUpdate == 1 && userUpdate == 1) {
								
								EventLog eventLogNew = new EventLog();
								
								Date dateNow = new Date();
								
								eventLogNew.setUserId(user);
								eventLogNew.setEventLogDate(dateNow);
								eventLogNew.setEventLogDescription( "The specialist was updated correctly");
								eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
								
								iEventLogService.save(eventLogNew);
								
								userResponse.put("statusCode", HttpStatus.OK.value());
								userResponse.put("message", "Se actualizo el ESPECIALISTA correctamente.");
				
								response.setStatus(HttpServletResponse.SC_OK);
								return userResponse;
								
							}else {
								
								userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
								userResponse.put("message", "El PACIENTE no existe.");
								
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
			exceptionLogNew.setExceptionLogController("SpecialistRestController");
			exceptionLogNew.setExceptionLogMethod("updateSpecialistBySpecialistId");
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
