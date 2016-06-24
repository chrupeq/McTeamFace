package com.ait.db.data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.db.model.User_equipment;

@Stateless
@LocalBean
public class UETypeDAO {

	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<User_equipment> getAllUniqueModels() {
		query = entityManager.createQuery("SELECT DISTINCT manufacturer, model, tac FROM User_equipment");
		List<User_equipment> distinctModels = query.getResultList();
		System.out.println(distinctModels.size());
		return distinctModels;
    }
	
}
