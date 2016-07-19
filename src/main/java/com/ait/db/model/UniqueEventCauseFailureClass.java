package com.ait.db.model;

public class UniqueEventCauseFailureClass {
	private Event_cause event_cause;
	private Failure_class failureClass;
	
	public UniqueEventCauseFailureClass(Event_cause event_cause, Failure_class failureClass) {
		this.event_cause = event_cause;
		this.failureClass = failureClass;
	}

	public Event_cause getCauseCode() {
		return event_cause;
	}

	public Failure_class getFailureClass() {
		return failureClass;
	}

}
