package com.ait.db.data;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ait.db.model.Session;

@Stateless
@LocalBean
public class SessionDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	
	public void addEntry(final String username, final String sessionId, final String jobTitle){
		final Session session = new Session(sessionId, jobTitle, username);
		entityManager.persist(session);
	}
	
}
