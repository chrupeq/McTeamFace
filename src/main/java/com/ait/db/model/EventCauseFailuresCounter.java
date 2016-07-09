package com.ait.db.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EventCauseFailuresCounter {
	
	private Event_causeKey eventCauseKey;
	private List<Event_cause> eventCauseList;
	private List<EventCauseFailures> eventCauseFailuresList;
	
	public EventCauseFailuresCounter(List<Event_cause> eventCauseList){
		this.eventCauseList = eventCauseList;
	}
	
	private Event_causeKey createEventCauseKeyObject(Event_cause eventCause){
		int eventId = eventCause.getEvent_id();
		int causeCode = eventCause.getCause_code();
		Event_causeKey eventCauseKey = new Event_causeKey(causeCode, eventId);
		return eventCauseKey;
	}
	
	private Map<Event_causeKey, Integer> getNumberOfFailures() {
		Map<Event_causeKey, Integer> numberOfFailures = new HashMap<>();
		for(Event_cause eventCause : eventCauseList) {
			Event_causeKey eventCauseKey = createEventCauseKeyObject(eventCause);
			if(numberOfFailures.containsKey(eventCauseKey)){
				int numOfFailures = numberOfFailures.get(eventCauseKey);
				++numOfFailures;
				numberOfFailures.put(eventCauseKey, numOfFailures);
			} else {
				numberOfFailures.put(eventCauseKey, 1);
			}
		}
		return numberOfFailures;
	}
	public List<EventCauseFailures> getEventCauseFailures() {
		Map<Event_causeKey, Integer> numberOfFailures = getNumberOfFailures();
		eventCauseFailuresList = new ArrayList<>();
		for(Event_causeKey key : numberOfFailures.keySet()){
			int eventID = key.getEvent_id();
			int causeCode = key.getCause_code();
			int numOfFailures = numberOfFailures.get(key);
			EventCauseFailures eventCauseFailures = 
					new EventCauseFailures(eventID, causeCode, numOfFailures);
			eventCauseFailuresList.add(eventCauseFailures);
		}
		Collections.sort(eventCauseFailuresList);
		return eventCauseFailuresList;
	}
	
}
