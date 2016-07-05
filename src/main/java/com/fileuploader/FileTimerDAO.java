package com.fileuploader;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@LocalBean
public class FileTimerDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public FileTimer getFileTimer(int id) {
		return entityManager.find(FileTimer.class, id);
	}
	
	public void update(final FileTimer fileTimer) {
		entityManager.merge(fileTimer);
	}
	
}