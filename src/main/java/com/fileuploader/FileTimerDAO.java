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
	
	public FileTimer getFileTimer(final int fileTimerId) {
		return entityManager.find(FileTimer.class, fileTimerId);
	}
	
	public void update(final FileTimer fileTimer) {
		fileTimer.setId(1);
		entityManager.merge(fileTimer);
	}
	
}