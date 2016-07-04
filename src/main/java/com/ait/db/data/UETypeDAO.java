package com.ait.db.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.db.model.Base_data;
import com.ait.db.model.User_equipment;

@Stateless
@LocalBean
public class UETypeDAO {

	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<User_equipment> getAllUniqueModels() {
		query = entityManager.createQuery("SELECT DISTINCT(b.user_equipment) FROM Base_data b");
		List<User_equipment> distinctModels = query.getResultList();
		System.out.println(distinctModels.size());
//		List<User_equipment> equipmentList = new ArrayList<User_equipment>();
		
//		for(Base_data b : distinctModels){
//			User_equipment ue = new User_equipment();
//			ue = b.getUser_equipment();
//			equipmentList.add(ue);
//			
//		}
		return distinctModels;
    }
	
}
