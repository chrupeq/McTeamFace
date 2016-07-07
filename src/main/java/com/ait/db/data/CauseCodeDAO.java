package com.ait.db.data;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@LocalBean
public class CauseCodeDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<Integer> getUniqueCauseCodes(){
		query = entityManager.createQuery("SELECT DISTINCT(event_cause.cause_code) FROM Base_data b");
		List<Integer> causeCodeList = query.getResultList();
		Collections.sort(causeCodeList);
		return causeCodeList;
	}
	public List<BigInteger> getAffectedIMSIs(int causeCode){
		query = entityManager.createQuery("SELECT DISTINCT(b.imsi) FROM Base_data b WHERE event_cause.cause_code = :causeCode");
		query.setParameter("causeCode", causeCode);
		List<BigInteger> affectedIMSIList = query.getResultList();
		return affectedIMSIList;
	}
}
