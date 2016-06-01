package com.ait.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Event_cause {
	
	private int cause_code;
	@Id
	private int event_id;
	private String description;
	
	public Event_cause() {}
	
	public Event_cause(int cause_code, int event_id, String description) {
		super();
		this.cause_code = cause_code;
		this.event_id = event_id;
		this.description = description;
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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
