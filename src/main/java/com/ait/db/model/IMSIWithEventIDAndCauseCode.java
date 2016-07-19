package com.ait.db.model;

import java.math.BigInteger;

public class IMSIWithEventIDAndCauseCode {
	private int event_id;
	private String cause_code;
	private String eventIdCauseCodeDescription;
	
	private String failure_class;
	private String failureDescription;
	private BigInteger imsi;
	
	public IMSIWithEventIDAndCauseCode(int event_id, String cause_code, 
			String eventIdCauseCodeDescription, String failure_class, String failureDescription, BigInteger imsi) {
		super();
		this.event_id = event_id;
		this.cause_code = cause_code;
		this.eventIdCauseCodeDescription = eventIdCauseCodeDescription;
		this.failure_class = failure_class;
		this.failureDescription = failureDescription;
		this.imsi = imsi;
	}

	public int getEvent_id() {
		return event_id;
	}

	public String getCause_code() {
		return cause_code;
	}

	public void setCause_code(String causeCode) {
		this.cause_code = causeCode;
	}
	public String getFailure_class() {
		return failure_class;
	}
	public BigInteger getImsi() {
		return imsi;
	}
	public String getFailureDescription() {
		return failureDescription;
	}
	public String getEventIdCauseCodeDescription() {
		return eventIdCauseCodeDescription;
	}
	@Override
	public boolean equals(Object other){
		if(other instanceof IMSIWithEventIDAndCauseCode){
			IMSIWithEventIDAndCauseCode othr = (IMSIWithEventIDAndCauseCode) other;
			if(this.cause_code.equals(othr.cause_code)
				&& this.event_id == othr.event_id
				&& this.failure_class.equals(othr.failure_class)
				&& this.imsi.equals(othr.imsi)) {
				return true;
			}
		}
		return false;	
	}
	@Override
	public int hashCode(){
		return getConcatenatedString().hashCode();
	}
	private String getConcatenatedString(){
		String causeCode = cause_code;
		String failureClass = failure_class;
		causeCode += failureClass;
		return causeCode;
	}
}
