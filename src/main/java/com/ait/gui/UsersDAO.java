package com.ait.gui;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.db.model.User;

/**
 * Users Data Access Object Class
 *
 */
@Stateless
@LocalBean
public class UsersDAO {

	@PersistenceContext
	private EntityManager entityManager;

	public List<User> getAllUsers() {
		final Query query = entityManager.createQuery("SELECT u FROM User u");
		return query.getResultList();
	}

	public User getUser(final int userId) {
		return entityManager.find(User.class, userId);
	}

	public void save(final User user) {
		entityManager.persist(user);
	}

	public void update(final User user) {
		entityManager.merge(user);
	}

	public void delete(final int userId) {
		entityManager.remove(getUser(userId));
	}
	
	public void updateLastLogin(final String username, final String newLogin) {
		final Query query = entityManager.createQuery("UPDATE User u SET last_login = :loginTime WHERE username = :username");
		query.setParameter("loginTime", newLogin)
		.setParameter("username", username);
		query.executeUpdate();
	}

}
