package com.ait.db.data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.db.model.Event_cause;

@Stateless
@LocalBean
public class EventCauseFailuresDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<Event_cause> getAllEventCauseCodesPerPhone(final int tacNumber){
		
		query = entityManager.createQuery("SELECT event_cause FROM Base_data b WHERE ue_type = :tacNumber");
		query.setParameter("tacNumber", tacNumber);
		List<Event_cause> eventCauseList = query.getResultList();
		return eventCauseList;
	}
}
