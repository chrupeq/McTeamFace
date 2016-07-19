package com.ait.db.model;

public class UniqueEventCauseFailureClass {
	private int causeCode;
	private int failureClass;
	private String description;
	
	public UniqueEventCauseFailureClass(int causeCode, int failureClass, String description) {
		this.causeCode = causeCode;
		this.failureClass = failureClass;
		this.description = description;
	}

	public int getCauseCode() {
		return causeCode;
	}

	public int getFailureClass() {
		return failureClass;
	}

	public String getDescription() {
		return description;
	}

	
	
}
