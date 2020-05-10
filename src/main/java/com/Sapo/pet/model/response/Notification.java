package com.Sapo.pet.model.response;

public class Notification {

	private boolean status;
	private String message;
	
	public Notification() {
		
	}
	
	public Notification(boolean status, String message) {
		this.message = message;
		this.status = status;
	}
	
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
