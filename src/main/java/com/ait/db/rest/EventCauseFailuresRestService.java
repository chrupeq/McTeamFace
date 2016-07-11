package com.ait.db.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.db.data.EventCauseFailuresDAO;
import com.ait.db.model.EventCauseFailures;
import com.ait.db.model.EventCauseFailuresCounter;
import com.ait.db.model.Event_cause;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/event_cause_failures")
@Stateless
@LocalBean
public class EventCauseFailuresRestService {
	
	@EJB
	EventCauseFailuresDAO eventCauseFailuresDAO;
	@PersistenceContext
	private EntityManager entityManager;
	private EventCauseFailuresCounter eventCauseFailuresCounter;
	
	@GET
	@Path("/count_for_tac")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getEventCauseFailuresForATac(@QueryParam("tac") int tacNumber) {
		try {
			List<Event_cause> eventCauseList 
				= eventCauseFailuresDAO.getAllEventCauseCodesPerPhone(tacNumber);
			if(eventCauseList.isEmpty()) {
				return Response.status(404).build();
			}
			eventCauseFailuresCounter = new EventCauseFailuresCounter(eventCauseList);
			List<EventCauseFailures> eventCauseFailuresList 
				= eventCauseFailuresCounter.getEventCauseFailures();
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(eventCauseFailuresList);
			return Response.status(200).entity(eventCauseFailuresList).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}
		
	}
	

}
