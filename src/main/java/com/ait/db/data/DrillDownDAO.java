package com.ait.db.data;

import java.math.BigInteger;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.ait.db.model.Base_data;

@Stateless
@LocalBean
public class DrillDownDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<Base_data> getFailureEventDesc(final int eventId, final int causeCode, final int tacNumber) {
		query = entityManager.createQuery("SELECT b FROM Base_data b WHERE event_cause.event_id = :eventId AND event_cause.cause_code = :causeCode AND ue_type = :tacNumber");
		query.setParameter("eventId", eventId);
		query.setParameter("causeCode", causeCode);
		query.setParameter("tacNumber", tacNumber);
		List<Base_data> baseDataList = query.getResultList();
		System.out.println(baseDataList.size()+" is the size boyo");
		return baseDataList;
    }
	
	public List<Base_data> getModelOfPhoneDesc(final BigInteger imsi) {
		query = entityManager.createQuery("SELECT b FROM Base_data b WHERE imsi = :imsi GROUP BY market, operator");
		query.setParameter("imsi", imsi);
		List<Base_data> baseDataList = query.getResultList();
		System.out.println(baseDataList.size()+" is the size boyo");
		return baseDataList;
    }

}
