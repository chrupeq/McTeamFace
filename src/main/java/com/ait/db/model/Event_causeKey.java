package com.ait.db.model;

import java.io.Serializable;

public class Event_causeKey implements Serializable, CompositePK{

	private static final long serialVersionUID = 1L;
	private int cause_code;
	private int event_id;
	
	public Event_causeKey() {
		
	}
	public Event_causeKey(int cause_code, int event_id) {
		super();
		this.cause_code = cause_code;
		this.event_id = event_id;
	}
	public int getCause_code() {
		return cause_code;
	}
	public void setCause_code(int cause_code) {
		this.cause_code = cause_code;
	}
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}

	public int getUniqueIdentifier() {
		return this.cause_code + this.event_id;
	}
	@Override
	public boolean equals(Object object) {
		if(object instanceof Event_causeKey) {
			Event_causeKey other = (Event_causeKey) object;
			return this.getUniqueIdentifier() == other.getUniqueIdentifier();
		}
		return false;
	}
	@Override 
	public int hashCode() {
		String sumOfCauseAndEvent = Integer.toString(this.getUniqueIdentifier());
		return sumOfCauseAndEvent.hashCode();
	}
}
