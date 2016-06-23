package com.ait.db.data;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.db.model.IMSI;
import com.ait.db.model.NetworkEntity;

@Stateless
@LocalBean
public class IMSIDAO {

	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<IMSI> getAllUniqueIMSIs() {
		query = entityManager.createQuery("SELECT DISTINCT imsi FROM Base_data");
		List<IMSI> distinctIMSIs = query.getResultList();
		System.out.println(distinctIMSIs.size());
		return distinctIMSIs;
    }
	
}
