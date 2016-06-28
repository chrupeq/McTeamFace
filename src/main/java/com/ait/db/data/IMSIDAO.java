package com.ait.db.data;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.ait.db.model.Base_data;
import com.ait.db.model.IMSIWithFailuresFactory;
import com.ait.db.model.IMSIWithValidFailureClasses;

@Stateless
@LocalBean
public class IMSIDAO {

	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	
	public List<BigInteger> getAllUniqueIMSIs() {
		query = entityManager.createQuery("SELECT DISTINCT(i.imsi) FROM Base_data i");
		List<BigInteger> imsisAsBigInts = query.getResultList();
//		List<IMSI> distinctIMSIs = IMSIFactory.getIMSIObjects(imsisAsBigInts);
		return imsisAsBigInts;
    }
	public Calendar[] parseStringIntoCalendarObject(String date1, String date2) throws ParseException {
		String[] dateArray = new String[2];
		dateArray[0] = date1;
		dateArray[1] = date2;
		Calendar[] calendarArray = new Calendar[2];
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		for(int i=0; i < dateArray.length; i++) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(simpleDateFormat.parse(dateArray[i]));
			calendarArray[i] = calendar;
		}
		return calendarArray;
	}
	public List<IMSIWithValidFailureClasses> getIMSIsByDates(String date1, String date2) throws ParseException{
		Calendar[] dateArray = parseStringIntoCalendarObject(date1, date2);
		query = entityManager.createQuery("SELECT i FROM Base_data i WHERE i.date_time BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", dateArray[0]);
		query.setParameter("endDate", dateArray[1]);
		List<Base_data> IMSIsBetweenDates = query.getResultList();
		List<IMSIWithValidFailureClasses> imsiWithValidFailureClasses = IMSIWithFailuresFactory.getImsiFailureClassList(IMSIsBetweenDates);
		return imsiWithValidFailureClasses;
	}
}
