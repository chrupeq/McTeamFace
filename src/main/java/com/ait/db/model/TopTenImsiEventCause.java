package com.ait.db.model;

public class TopTenImsiEventCause {
	

	private int eventId;
	private String eventDescription;
	private int causeCode;
	private String failureDescription;
	private String operator;
	private String country;
	
	public TopTenImsiEventCause(int eventId, String eventDescription, int causeCode, String failureDescription, String operator, String country){
		
	
		this.eventId = eventId;
		this.eventDescription = eventDescription;
		this.causeCode = causeCode;
		this.failureDescription = failureDescription;
		this.operator = operator;
		this.country = country;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public int getEventId() {
		return eventId;
	}

	public void setEventId(int eventId) {
		this.eventId = eventId;
	}

	public String getEventDescription() {
		return eventDescription;
	}

	public void setEventDescription(String eventDescription) {
		this.eventDescription = eventDescription;
	}

	public int getCauseCode() {
		return causeCode;
	}

	public void setCauseCode(int causeCode) {
		this.causeCode = causeCode;
	}

	public String getFailureDescription() {
		return failureDescription;
	}

	public void setFailureDescription(String failureDescription) {
		this.failureDescription = failureDescription;
	}
	
	


	
}
