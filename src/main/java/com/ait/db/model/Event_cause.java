package com.ait.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="event_cause")
@IdClass(Event_causeKey.class)
public class Event_cause implements Serializable, NetworkEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer cause_code;
	@Id
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private Integer event_id;
	@Column(nullable = false)
	private String description;
	@OneToMany(mappedBy="event_cause")
	List<Base_data> baseDataList;
	
	public Event_cause() {
		
	}
	public Event_cause(Integer causeCode, Integer eventId, String description) {
		cause_code = causeCode;
		event_id = eventId;
		this.description = description;
	}
	
	public Integer getCause_code() {
		return cause_code;
	}
	public void setCause_code(int cause_code) {
		this.cause_code = cause_code;
	}
	public Integer getEvent_id() {
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
	@Override
	public String toString() {
		return "Event_Cause: cause code: " + cause_code + " event id: " + event_id + " description: " + description;
	}

}
