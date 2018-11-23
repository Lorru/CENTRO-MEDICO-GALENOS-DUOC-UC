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
import cl.duoc.medicalcenter.galenos.api.models.entity.ProfileRole;
import cl.duoc.medicalcenter.galenos.api.models.entity.User;
import cl.duoc.medicalcenter.galenos.api.models.service.IEventLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IPatientService;
import cl.duoc.medicalcenter.galenos.api.models.service.IPersonalService;
import cl.duoc.medicalcenter.galenos.api.models.service.IProfileRoleService;
import cl.duoc.medicalcenter.galenos.api.models.service.ISpecialistService;
import cl.duoc.medicalcenter.galenos.api.models.service.IUserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserRestController {

	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	@Autowired
	private IEventLogService iEventLogService;
	
	@Autowired
	private IProfileRoleService iProfileRoleService;
	
	@Autowired
	private ISpecialistService iSpecialistService;
	
	@Autowired 
	private IPersonalService iPersonalService;
	
	@Autowired
	private IPatientService iPatientService;
	
	@PostMapping("/login")
	public Map<String, Object> login(HttpServletResponse response , @RequestBody User user){
		
		Map<String, Object> userResponse = new HashMap<String, Object>();
		
		try {
			
			if(user.getUserPassword() == null || user.getUserPassword() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "La CLAVE es requerida.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientEmail() == null || user.getPatientId().getPatientEmail() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El CORREO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getSpecialistId().getSpecialistEmail() == null || user.getSpecialistId().getSpecialistEmail() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El CORREO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPersonalId().getPersonalEmail() == null || user.getPersonalId().getPersonalEmail() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El CORREO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else {
				
				boolean resultLogin = false;
				
				User userExist = iUserService.login(user.getPatientId().getPatientEmail(), user.getSpecialistId().getSpecialistEmail(), user.getPersonalId().getPersonalEmail());
				
				if(userExist == null) {
					
					userResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					userResponse.put("message", "El CORREO o CLAVE son incorrectos");

					response.setStatus(HttpServletResponse.SC_OK);
					return userResponse;
					
				}else {
					
					resultLogin = passwordEncoder.matches(user.getUserPassword(), userExist.getUserPassword());
					
					if(resultLogin == false) {
						
						userResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
						userResponse.put("message", "El CORREO o CLAVE son incorrectos");

						response.setStatus(HttpServletResponse.SC_OK);
						return userResponse;
						
					}else {
						
						EventLog eventLogNew = new EventLog();
						
						Date dateNow = new Date();
						
						eventLogNew.setUserId(userExist);
						eventLogNew.setEventLogDate(dateNow);
						eventLogNew.setEventLogDescription( "The user successfully logged in");
						eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
						
						iEventLogService.save(eventLogNew);
						
						final String KEY_STRING = "medical-center-galenos-key";
						final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());

						String token = Jwts.builder().setSubject(userExist.toString()).signWith(SignatureAlgorithm.HS512,KEY).setIssuedAt(new Date()).setExpiration(new Date(System.currentTimeMillis() + 1800000)).compact();
						
						List<ProfileRole> profileRole = iProfileRoleService.findAllByProfileId(userExist.getProfileId().getProfileId());
						
						userResponse.put("statusCode", HttpStatus.OK.value());
						userResponse.put("userConnectSuccess", userExist);
						userResponse.put("profileRole", profileRole);
						userResponse.put("token", token);

						response.setStatus(HttpServletResponse.SC_OK);
						return userResponse;
					}
				}
				
			}
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			boolean resultLogin = false;
			
			User userExist = iUserService.login(user.getPatientId().getPatientEmail(), user.getSpecialistId().getSpecialistEmail(), user.getPersonalId().getPersonalEmail());
			
			if(userExist != null) {
				
				resultLogin = passwordEncoder.matches(user.getUserPassword(), userExist.getUserPassword());
				
				if(resultLogin) {
					
					exceptionLogNew.setUserId(userExist);
					
				}
				
			}
			
			exceptionLogNew.setExceptionLogController("UserRestController");
			exceptionLogNew.setExceptionLogMethod("login");
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
	
	@GetMapping("/findByUserIdAndStatusActive/{userId}")
	public Map<String, Object> findByUserIdAndStatusActive(HttpServletResponse response, @PathVariable("userId") long userId, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> userResponse = new HashMap<String, Object>();
		
		try {
			
				if(token == null || token == "") {
				
					userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
					userResponse.put("message", "El TOKEN es requerido.");
	
					response.setStatus(HttpServletResponse.SC_OK);
					return userResponse;
				
				}else {
					
					final String KEY_STRING = "medical-center-galenos-key";
					final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
					
					try {
						
						Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
						
						
						if(Long.toString(userId) == null || Long.toString(userId) == ""){
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El USUARIO es requerido.");

							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else {
							
							User userExist = iUserService.findByUserIdAndStatusActive(userId);
							
							if(userExist != null) {
								

								userResponse.put("statusCode", HttpStatus.OK.value());
								userResponse.put("userExits", userExist);
								
								response.setStatus(HttpServletResponse.SC_OK);
								return userResponse;
								
							}else {
								
								userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
								userResponse.put("message", "El USUARIO no existe.");
								
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
			
			exceptionLogNew.setExceptionLogController("UserRestController");
			exceptionLogNew.setExceptionLogMethod("findByUserIdAndStatusActive");
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
					
					List<User> userList = iUserService.findAllUser(PageRequest.of(page, size));
					List<User> userListCount = iUserService.findAllUserCount();
					
					if(userList.size() > 0) {
						
						specialistResponse.put("user", userList);
						specialistResponse.put("statusCode", HttpStatus.OK.value());
						specialistResponse.put("countRows", userListCount.size());
						specialistResponse.put("currentPage", page);
						specialistResponse.put("countRowsByPage", userList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return specialistResponse;
						
					}else {
						
						specialistResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						specialistResponse.put("countRows", userList.size());
						
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
			
			exceptionLogNew.setExceptionLogController("UserRestController");
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
	
	@PutMapping("/updateUserByUserIdAndUserStatus")
	public Map<String, Object> updateUserByUserIdAndUserStatus(HttpServletResponse response, @RequestBody User user, @RequestHeader("Authorization") String token){
		
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
						
						if(Long.toString(user.getUserId()) == null || Long.toString(user.getUserId()) == "") {
							
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El USUARIO es requerido.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else if(String.valueOf(user.getUserStatus()) == null || String.valueOf(user.getUserStatus()) == "") {
						
							userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
							userResponse.put("message", "El STATUS es requerido.");
			
							response.setStatus(HttpServletResponse.SC_OK);
							return userResponse;
							
						}else {
							
							User userExist = iUserService.findByUserId(user.getUserId());
							
							int userUpdate = iUserService.updateUserByUserIdAndUserStatus(user.getUserId(), user.getUserStatus());
							
							if(userExist.getProfileId().getProfileId() == 2) {
								
								int specialistUpdate = iSpecialistService.updateSpecialistBySpecialistIdAndSpecialistStatus(userExist.getSpecialistId().getSpecialistId(), user.getUserStatus());
								
								if(userUpdate == 1 && specialistUpdate == 1) {
									
									EventLog eventLogNew = new EventLog();
									
									Date dateNow = new Date();
									
									eventLogNew.setUserId(userExist);
									eventLogNew.setEventLogDate(dateNow);
									eventLogNew.setEventLogDescription( "The user was updated correctly");
									eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
									
									iEventLogService.save(eventLogNew);
									
									userResponse.put("statusCode", HttpStatus.OK.value());
									userResponse.put("message", "Se actualizo el usuario correctamente.");
					
									response.setStatus(HttpServletResponse.SC_OK);
									return userResponse;
									
								}else {
									
									userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
									userResponse.put("message", "El USUARIO no existe.");
									
									response.setStatus(HttpServletResponse.SC_OK);
									return userResponse;
									
								}
								
							}else if(userExist.getProfileId().getProfileId() == 5) {
								
								int patientUpdate = iPatientService.updatePatientByPatientIdAndPatientStatus(userExist.getPatientId().getPatientId(), user.getUserStatus());
								
								if(userUpdate == 1 && patientUpdate == 1) {
									
									EventLog eventLogNew = new EventLog();
									
									Date dateNow = new Date();
									
									eventLogNew.setUserId(userExist);
									eventLogNew.setEventLogDate(dateNow);
									eventLogNew.setEventLogDescription( "The user was updated correctly");
									eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
									
									iEventLogService.save(eventLogNew);
									
									userResponse.put("statusCode", HttpStatus.OK.value());
									userResponse.put("message", "Se actualizo el usuario correctamente.");
					
									response.setStatus(HttpServletResponse.SC_OK);
									return userResponse;
									
								}else {
									
									userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
									userResponse.put("message", "El USUARIO no existe.");
									
									response.setStatus(HttpServletResponse.SC_OK);
									return userResponse;
									
								}
								
							}else {
								
								int personalUpdate = iPersonalService.updatePersonalByPersonalIdAndPersonalStatus(userExist.getPersonalId().getPersonalId(), user.getUserStatus());
								
								if(userUpdate == 1 && personalUpdate == 1) {
									
									EventLog eventLogNew = new EventLog();
									
									Date dateNow = new Date();
									
									eventLogNew.setUserId(userExist);
									eventLogNew.setEventLogDate(dateNow);
									eventLogNew.setEventLogDescription( "The user was updated correctly");
									eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
									
									iEventLogService.save(eventLogNew);
									
									userResponse.put("statusCode", HttpStatus.OK.value());
									userResponse.put("message", "Se actualizo el usuario correctamente.");
					
									response.setStatus(HttpServletResponse.SC_OK);
									return userResponse;
									
								}else {
									
									userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
									userResponse.put("message", "El USUARIO no existe.");
									
									response.setStatus(HttpServletResponse.SC_OK);
									return userResponse;
									
								}
								
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
			exceptionLogNew.setExceptionLogController("UserRestController");
			exceptionLogNew.setExceptionLogMethod("updateUserByUserIdAndUserStatus");
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
