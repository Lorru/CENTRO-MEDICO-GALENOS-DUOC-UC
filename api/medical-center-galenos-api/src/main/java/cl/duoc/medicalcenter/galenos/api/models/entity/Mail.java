package cl.duoc.medicalcenter.galenos.api.models.entity;

import java.util.Map;

public class Mail {

	private String to;
	private String subject;
	private String message;
	private Map<String, Object> htmlVariables;
	
	public Mail() {
		
	}
	

	public Mail(String to, String subject, String message, Map<String, Object> htmlVariables) {
		this.to = to;
		this.subject = subject;
		this.message = message;
		this.htmlVariables = htmlVariables;
	}


	public String getTo() {
		return to;
	}
	
	public void setTo(String to) {
		this.to = to;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public void setSubject(String subject) {
		this.subject = subject;
	}

	public Map<String, Object> getHtmlVariables() {
		return htmlVariables;
	}

	public void setHtmlVariables(Map<String, Object> htmlVariables) {
		this.htmlVariables = htmlVariables;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
