package com.ait.db.data;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.db.model.Base_data;
import com.ait.db.model.IMSI;

@Stateless
@LocalBean
public class IMSIDAO {

	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<IMSI> getAllUniqueIMSIs() {
		query = entityManager.createQuery("SELECT DISTINCT imsi FROM Base_data");
		List<IMSI> distinctIMSIs = query.getResultList();
		return distinctIMSIs;
    }
	
	public List<Base_data> getIMSIsByDates(String date1, String date2){

		query = entityManager.createQuery("SELECT i FROM Base_data i WHERE date_time BETWEEN '" + date1 + "' AND '" + date2 + "'");
//		query.setParameter("date1", t);
//		query.setParameter("date2", u);
		
		
		List<Base_data> iMSIsBetweenDates = query.getResultList();
		System.out.println(iMSIsBetweenDates.size());
		return iMSIsBetweenDates;
	}
	
}
