package com.fileuploader;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * The FilerTimer Data Access Object Class
 * Used for getting the file timer back from
 * the database and updating the file timer
 *
 */
@Stateless
@LocalBean
public class FileTimerDAO {

	@PersistenceContext
	private EntityManager entityManager;
	
	public FileTimer getFileTimer(int id) {
		return entityManager.find(FileTimer.class, id);
	}
	
	public void update(final FileTimer fileTimer) {
		fileTimer.setId(1);
		entityManager.merge(fileTimer);
	}
	
}