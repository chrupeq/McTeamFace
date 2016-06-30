package com.ait.db.data;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
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
	private DateParser dateParser;
	
	public List<BigInteger> getAllUniqueIMSIs() {
		query = entityManager.createQuery("SELECT DISTINCT(i.imsi) FROM Base_data i");
		List<BigInteger> imsisAsBigInts = query.getResultList();
		return imsisAsBigInts;
    }
	public List<IMSIWithValidFailureClasses> getIMSIsByDates(String date1, String date2) {
		
		dateParser = new DateParser();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
		query = entityManager.createQuery("SELECT i FROM Base_data i WHERE i.date_time BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", calendarArray[0]);
		query.setParameter("endDate", calendarArray[1]);
		List<Base_data> IMSIsBetweenDates = query.getResultList();
		List<IMSIWithValidFailureClasses> imsiWithValidFailureClasses = IMSIWithFailuresFactory.getImsiFailureClassList(IMSIsBetweenDates);
		Collections.sort(imsiWithValidFailureClasses);
		return imsiWithValidFailureClasses;
	}
//	public List<IMSIWithEventIDAndCauseCode> getIMSIsWithEventIDsAndCauseCodes(BigInteger imsi) {
	public List<Base_data> getIMSIsWithEventIDsAndCauseCodes(BigInteger imsi) {
		query = entityManager.createQuery("SELECT b FROM Base_data b WHERE b.imsi = :imsi");
		query.setParameter("imsi", imsi);
		List<Base_data> baseDataWithIMSI = query.getResultList();
		return baseDataWithIMSI;
	}
	
}
