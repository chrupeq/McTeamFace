package com.ait.db.data;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.ait.db.model.Base_data;
import com.ait.db.model.TopTenIMSIForFailures;
import com.ait.db.model.TopTenImsiEventCause;

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
	
	public List<Base_data> getFailureCountsForCountryByEventIdAndCauseCode(String country) {
	
		query = entityManager.createQuery(
				"SELECT b FROM Base_data b WHERE b.mcc_mnc.country = :country");
	
		query.setParameter("country", country);
		
		final List<Base_data> baseDataList = query.getResultList();
		System.out.println(baseDataList.size()+" is the size boyo");
		
		return baseDataList;
    }
	
	public List<TopTenImsiEventCause> getTopTenImsiEventIdCauseCode(BigInteger imsi){
		
		query = entityManager.createQuery("SELECT b.event_cause.event_id, b.event_cause.description, b.event_cause.cause_code, b.failure_class.description, b.mcc_mnc.operator, b.mcc_mnc.country FROM Base_data b WHERE imsi= :imsi");
	
		query.setParameter("imsi", imsi);
		final List<Object> topTenIMSIData = query.getResultList();
		final List<TopTenImsiEventCause> topTenIMSIListOfHelperObjects = convertTopTenImsiEventCause(topTenIMSIData);
		
		return topTenIMSIListOfHelperObjects;
	}	
	
	public List<TopTenImsiEventCause> convertTopTenImsiEventCause(List<Object> listOfObjects){
		
		long numFailures;
		List<TopTenImsiEventCause> topTenIMSIList = new ArrayList<TopTenImsiEventCause>();
		for(final Object object : listOfObjects){
			final Object[] objectArray = (Object[]) object;
			
			
			final TopTenImsiEventCause topTenIMSIForFailures = new TopTenImsiEventCause(Integer.parseInt(objectArray[0].toString()), objectArray[1].toString(), Integer.parseInt(objectArray[2].toString()), objectArray[3].toString(), objectArray[4].toString(), objectArray[5].toString());
			topTenIMSIList.add(topTenIMSIForFailures);
		}
		
		if(topTenIMSIList.size() > 10) {
			topTenIMSIList = topTenIMSIList.subList(0, 10);
		}
		return topTenIMSIList;
	}

}
