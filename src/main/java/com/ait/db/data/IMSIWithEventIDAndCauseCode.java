package com.ait.db.data;

public class IMSIWithEventIDAndCauseCode {
	private int event_id;
	private String cause_code;
	private String failure_class;
	
	public IMSIWithEventIDAndCauseCode(int event_id, String cause_code, String failure_class) {
		super();
		this.event_id = event_id;
		this.cause_code = cause_code;
		this.failure_class = failure_class;
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
}
