package cl.duoc.medicalcenter.galenos.api.controllers;

import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.medicalcenter.galenos.api.models.entity.EventLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.ExceptionLog;
import cl.duoc.medicalcenter.galenos.api.models.entity.Patient;
import cl.duoc.medicalcenter.galenos.api.models.entity.Profile;
import cl.duoc.medicalcenter.galenos.api.models.entity.User;
import cl.duoc.medicalcenter.galenos.api.models.service.IEventLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IExceptionLogService;
import cl.duoc.medicalcenter.galenos.api.models.service.IPatientService;
import cl.duoc.medicalcenter.galenos.api.models.service.IUserService;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

@RestController
@RequestMapping("/api/patient")
@CrossOrigin
public class PatientRestController {

	@Autowired
	private IUserService iUserService;
	
	@Autowired
	private IPatientService iPatientService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private IEventLogService iEventLogService;
	
	@Autowired
	private IExceptionLogService iExceptionLogService;
	
	
	@PostMapping("/create")
	public Map<String, Object> create(HttpServletResponse response, @RequestBody User user){
		
		Map<String, Object> userResponse = new HashMap<String, Object>();
		
		
		try{
			
			if(user.getUserPassword() == null || user.getUserPassword() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "La CLAVE es requerida.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientRun() == null || user.getPatientId().getPatientRun() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El RUT es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientFirstName() == null || user.getPatientId().getPatientFirstName() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El PRIMER NOMBRE es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientSecondName() == null || user.getPatientId().getPatientSecondName() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El SEGUNDO NOMBRE es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientSurName() == null || user.getPatientId().getPatientSurName() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El PRIMER APELLIDO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientSecondSurName() == null || user.getPatientId().getPatientSecondSurName() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El SEGUNDO APELLIDO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientBirthDate() == null || user.getPatientId().getPatientBirthDate().toString() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "La FECHA DE NACIMIENTO es requerida.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientEmail() == null || user.getPatientId().getPatientEmail() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El CORREO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(Long.toString(user.getPatientId().getGenderId().getGenderId()) == null || Long.toString(user.getPatientId().getGenderId().getGenderId()) == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El GENERO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else {
				
				Patient patientExistByRun = iPatientService.findByRun(user.getPatientId().getPatientRun());
				Patient patientExistByEmail = iPatientService.findByEmail(user.getPatientId().getPatientEmail());
				
				
				if(patientExistByRun == null && patientExistByEmail == null) {
					
					String patientNewStatus = "1";
					String userNewStatus = "1";
					
					String patientFullNameNew = user.getPatientId().getPatientFirstName() + " " + user.getPatientId().getPatientSecondName() + " " + user.getPatientId().getPatientSurName() + " " + user.getPatientId().getPatientSecondSurName();
					
					user.getPatientId().setPatientStatus(patientNewStatus.charAt(0));
					user.getPatientId().setPatientFullName(patientFullNameNew);
					
					
					Patient patientNew = iPatientService.save(user.getPatientId());
					Profile profile = new Profile(5);
					
					user.setProfileId(profile);
					user.setPatientId(patientNew);
					user.setUserStatus(userNewStatus.charAt(0));
					String password = passwordEncoder.encode(user.getUserPassword());
					user.setUserPassword(password);
					
					User userNew = iUserService.save(user);
					
					EventLog eventLogNew = new EventLog();
					
					Date dateNow = new Date();
					
					eventLogNew.setUserId(userNew);
					eventLogNew.setEventLogDate(dateNow);
					eventLogNew.setEventLogDescription("Patient created successfully.");
					eventLogNew.setEventLogStatusCode(HttpStatus.CREATED.value());
					
					iEventLogService.save(eventLogNew);
					
					userResponse.put("patientNew", patientNew);
					userResponse.put("userNew", userNew);
					userResponse.put("statusCode", HttpStatus.CREATED.value());

					response.setStatus(HttpServletResponse.SC_OK);
					return userResponse;
					
				}else {
					
					userResponse.put("statusCode", HttpStatus.OK.value());
					userResponse.put("message", "El PACIENTE ya existe.");

					response.setStatus(HttpServletResponse.SC_OK);
					return userResponse;
				}
			}
						
		}catch(Exception ex) {
			
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("PatientRestController");
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
	
	@GetMapping("/findAll")
	public Map<String, Object> findAll(HttpServletResponse response, @RequestHeader(value="Authorization") String token){
		
		Map<String, Object> patientResponse = new HashMap<String, Object>();
		
		try{
			
			if(token == null || token == "") {
				
				patientResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				patientResponse.put("message", "El TOKEN es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return patientResponse;
				
			}else {
				
				final String KEY_STRING = "medical-center-galenos-key";
				final String KEY = Base64.getEncoder().encodeToString(KEY_STRING.getBytes());
				
				try {
					
					Jwts.parser().setSigningKey(KEY).parseClaimsJws(token);
					
					List<Patient> patientList = iPatientService.findAllPatientByStatusActive();
					
					if(patientList.size() > 0) {
						
						patientResponse.put("patient", patientList);
						patientResponse.put("statusCode", HttpStatus.OK.value());
						patientResponse.put("countRows", patientList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return patientResponse;
						
					}else {
						
						patientResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
						patientResponse.put("countRows", patientList.size());
						
						response.setStatus(HttpServletResponse.SC_OK);
						return patientResponse;
					}
					
				}catch(JwtException ex) {
					
					patientResponse.put("statusCode", HttpStatus.UNAUTHORIZED.value());
					patientResponse.put("message", "El TOKEN esta expirado.");

					response.setStatus(HttpServletResponse.SC_OK);
					return patientResponse;
				}
			}
			
			
		}catch(Exception ex) {
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setExceptionLogController("PatientRestController");
			exceptionLogNew.setExceptionLogMethod("findAll");
			exceptionLogNew.setExceptionLogDescription(ex.getMessage());
			exceptionLogNew.setExceptionLogDate(dateNow);
			exceptionLogNew.setExceptionLogStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
			
			iExceptionLogService.save(exceptionLogNew);
			
			patientResponse.put("statusCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
			patientResponse.put("message", "Houston tenemos un problema, vuelve intentarlo nuevamente.");
			
			response.setStatus(HttpServletResponse.SC_OK);
			return patientResponse;
		}
		
	}
	
	@PutMapping("/updatePatientByPatientId")
	public Map<String, Object> updatePatientByPatientId(HttpServletResponse response, @RequestBody User user){
		
		Map<String, Object> userResponse = new HashMap<String, Object>();
		
		
		try{
			
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
				
			}else if(Long.toString(user.getPatientId().getPatientId()) == null || Long.toString(user.getPatientId().getPatientId()) == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El PACIENTE es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientRun() == null || user.getPatientId().getPatientRun() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El RUT es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientFirstName() == null || user.getPatientId().getPatientFirstName() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El PRIMER NOMBRE es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientSecondName() == null || user.getPatientId().getPatientSecondName() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El SEGUNDO NOMBRE es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientSurName() == null || user.getPatientId().getPatientSurName() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El PRIMER APELLIDO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientSecondSurName() == null || user.getPatientId().getPatientSecondSurName() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El SEGUNDO APELLIDO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientBirthDate() == null || user.getPatientId().getPatientBirthDate().toString() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "La FECHA DE NACIMIENTO es requerida.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(user.getPatientId().getPatientEmail() == null || user.getPatientId().getPatientEmail() == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El CORREO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else if(Long.toString(user.getPatientId().getGenderId().getGenderId()) == null || Long.toString(user.getPatientId().getGenderId().getGenderId()) == "") {
				
				userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
				userResponse.put("message", "El GENERO es requerido.");

				response.setStatus(HttpServletResponse.SC_OK);
				return userResponse;
				
			}else {
				
				
				String patientFullName = user.getPatientId().getPatientFirstName() + " " + user.getPatientId().getPatientSecondName() + " " + user.getPatientId().getPatientSurName() + " " + user.getPatientId().getPatientSecondSurName();
				String password = passwordEncoder.encode(user.getUserPassword());
				
				int patientUpdate = iPatientService.updatePatientByPatientId(user.getPatientId().getPatientRun(), user.getPatientId().getPatientFirstName(), user.getPatientId().getPatientSecondName(), user.getPatientId().getPatientSurName(), user.getPatientId().getPatientSecondSurName(), patientFullName, user.getPatientId().getPatientBirthDate(), user.getPatientId().getPatientEmail(), user.getPatientId().getGenderId().getGenderId(), user.getPatientId().getPatientId());
				int userUpdate = iUserService.updateUserByUserId(password, user.getUserId());
				
				if(patientUpdate == 1 && userUpdate == 1) {
					
					EventLog eventLogNew = new EventLog();
					
					Date dateNow = new Date();
					
					eventLogNew.setUserId(user);
					eventLogNew.setEventLogDate(dateNow);
					eventLogNew.setEventLogDescription( "The patient was updated correctly");
					eventLogNew.setEventLogStatusCode(HttpStatus.OK.value());
					
					iEventLogService.save(eventLogNew);
					
					userResponse.put("statusCode", HttpStatus.OK.value());
					userResponse.put("message", "Se actualizo el PACIENTE correctamente.");
	
					response.setStatus(HttpServletResponse.SC_OK);
					return userResponse;
					
				}else {
					
					userResponse.put("statusCode", HttpStatus.NO_CONTENT.value());
					userResponse.put("message", "El PACIENTE no existe.");
					
					response.setStatus(HttpServletResponse.SC_OK);
					return userResponse;
					
				}
				
			}
						
		}catch(Exception ex) {
			
			
			ExceptionLog exceptionLogNew = new ExceptionLog();
			
			Date dateNow = new Date();
			
			exceptionLogNew.setUserId(user);
			exceptionLogNew.setExceptionLogController("PatientRestController");
			exceptionLogNew.setExceptionLogMethod("updatePatientByPatientId");
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
