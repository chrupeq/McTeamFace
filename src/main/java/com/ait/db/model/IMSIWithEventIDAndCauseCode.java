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
}
