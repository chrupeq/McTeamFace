package com.ait.db.model;



public class EventCauseFailures implements Comparable<EventCauseFailures> {
	private int event_id;
	private int cause_code;
	private Integer numberOfFailures;
	
	public EventCauseFailures(int event_id, int cause_code, Integer numberOfFailures) {
		super();
		this.event_id = event_id;
		this.cause_code = cause_code;
		this.numberOfFailures = numberOfFailures;
	}
	
	public int getEvent_id() {
		return event_id;
	}
	public int getCause_code() {
		return cause_code;
	}
	public Integer getNumberOfFailures() {
		return numberOfFailures;
	}
	@Override
	public int compareTo(EventCauseFailures that) {
		return that.numberOfFailures.compareTo(this.numberOfFailures);
	}
}
