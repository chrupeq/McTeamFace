package com.ait.db.data;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
	
	DateParser dateParser;
	
	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<Base_data> getFailureEventDesc(final int eventId, final int causeCode, final int tacNumber) {
		query = entityManager.createQuery("SELECT b FROM Base_data b WHERE event_cause.event_id = :eventId AND event_cause.cause_code = :causeCode AND ue_type = :tacNumber");
		query.setParameter("eventId", eventId);
		query.setParameter("causeCode", causeCode);
		query.setParameter("tacNumber", tacNumber);
		final List<Base_data> baseDataList = query.getResultList();
		System.out.println(baseDataList.size()+" is the size boyo");
		return baseDataList;
    }
	
	public List<Base_data> getModelOfPhoneDesc(final BigInteger imsi) {
		query = entityManager.createQuery("SELECT b FROM Base_data b WHERE imsi = :imsi");
		query.setParameter("imsi", imsi);
		final List<Base_data> baseDataList = query.getResultList();
		System.out.println(baseDataList.size()+" is the size boyo");
		return baseDataList;
    }
	
	public List<Base_data> getFailureDurationsBetweenDates(String date1, String date2, Long duration1, Long duration2) {
		
		dateParser = new DateParser();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
		
		query = entityManager.createQuery(
				"SELECT b FROM Base_data b WHERE b.date_time BETWEEN :date1 AND :date2 GROUP BY b.imsi HAVING SUM(b.duration) > :duration1 AND SUM(b.duration) <= :duration2");
		query.setParameter("date1", calendarArray[0]);
		query.setParameter("date2", calendarArray[1]);
		query.setParameter("duration1", duration1);
		query.setParameter("duration2", duration2);
		
		final List<Base_data> baseDataList = query.getResultList();
		System.out.println(baseDataList.size()+" is the size boyo");
		
		return baseDataList;
    }

}
