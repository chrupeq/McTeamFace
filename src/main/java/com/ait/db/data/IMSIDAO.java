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
		return imsisAsBigInts;
    }
	public Calendar[] parseStringIntoCalendarObject(String date1, String date2) throws ParseException {
		System.out.println("Inside the parse String method");
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
		System.out.println("inside the getIMSIByDates() method");
		String[] dateArray = new String[2];
		System.out.println("dateArray created");
		dateArray[0] = date1;
		System.out.println("Value one assigned to array; size: " + dateArray.length);
		dateArray[1] = date2;
		System.out.println("Value two assigned to array; size: " + dateArray.length);
		Calendar[] calendarArray = new Calendar[2];
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Before the for loop");
		for(int i=0; i < dateArray.length; i++) {
			System.out.println("Inside the for loop.");
			System.out.println("Iteration number:" + i);
			Calendar calendar = Calendar.getInstance();
			System.out.println("Created calendar instance");
			System.out.println("The current value from the dateArray: " + dateArray[i]);
			calendar.setTime(simpleDateFormat.parse(dateArray[i]));
			System.out.println("Have set time for the calendar object");
			calendarArray[i] = calendar;
		}
		
		System.out.println("After the for loop");
//		Calendar[] dateArray = parseStringIntoCalendarObject(date1, date2);
		query = entityManager.createQuery("SELECT i FROM Base_data i WHERE i.date_time BETWEEN :startDate AND :endDate");
//		query.setParameter("startDate", date1);
//		query.setParameter("endDate", date2);
		query.setParameter("startDate", calendarArray[0]);
		query.setParameter("endDate", calendarArray[1]);
		List<Base_data> IMSIsBetweenDates = query.getResultList();
		List<IMSIWithValidFailureClasses> imsiWithValidFailureClasses = IMSIWithFailuresFactory.getImsiFailureClassList(IMSIsBetweenDates);
		return imsiWithValidFailureClasses;
	}
}
