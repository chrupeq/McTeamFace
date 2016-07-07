package com.ait.db.model;

import java.math.BigInteger;

public class IMSIWithEventIDAndCauseCode {
	private int event_id;
	private String cause_code;
	private String failure_class;
	private BigInteger imsi;
	
	public IMSIWithEventIDAndCauseCode(int event_id, String cause_code, String failure_class, BigInteger imsi) {
		super();
		this.event_id = event_id;
		this.cause_code = cause_code;
		this.failure_class = failure_class;
		this.imsi = imsi;
	}

	public int getEvent_id() {
		return event_id;
	}

	public String getCause_code() {
		return cause_code;
	}

	public String getFailure_class() {
		return failure_class;
	}
	public BigInteger getImsi() {
		return imsi;
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
		cause_code += event_id;
		return cause_code.hashCode();
	}
}
