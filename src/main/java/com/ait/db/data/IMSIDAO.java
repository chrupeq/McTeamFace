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
import com.ait.db.model.Failure_class;
import com.ait.db.model.IMSIWithEventIDAndCauseCode;
import com.ait.db.model.IMSIWithEventIDAndCauseCodeFactory;
import com.ait.db.model.IMSIWithFailuresFactory;
import com.ait.db.model.IMSIWithValidFailureClasses;
import com.ait.db.model.TopTenIMSIForFailures;
import com.ait.db.model.UniqueEventCauseFailureClass;



@Stateless
@LocalBean
public class IMSIDAO {

	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	private DateParser dateParser;
	private IMSIWithEventIDAndCauseCodeFactory imsiWithEventIDAndCauseCodeFactory;
	
	
	public List<BigInteger> getAllUniqueIMSIs() {
		query = entityManager.createQuery("SELECT DISTINCT(i.imsi) FROM Base_data i");
		List<BigInteger> imsisAsBigInts = query.getResultList();
		return imsisAsBigInts;
    }
	public List<Base_data> getAllBaseDataByIMSIAndDate(BigInteger imsi, String dateOne, String dateTwo) {
		dateParser = new DateParser();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, dateOne, dateTwo);
		query = entityManager.createQuery("SELECT b FROM Base_data b WHERE b.imsi = :imsi AND b.date_time BETWEEN :startDate AND :endDate");
		query.setParameter("imsi", imsi);
		query.setParameter("startDate", calendarArray[0]);
		query.setParameter("endDate", calendarArray[1]);
		List<Base_data> baseDataList = query.getResultList();
		System.out.println(baseDataList.size());
		return baseDataList;
		
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
		System.out.println("failures" + imsiWithValidFailureClasses.size());
		Collections.sort(imsiWithValidFailureClasses);
		return imsiWithValidFailureClasses;
	}
	public List<IMSIWithEventIDAndCauseCode> getIMSIsWithEventIDsAndCauseCodes(BigInteger imsi) throws Exception {
		query = entityManager.createQuery("SELECT b FROM Base_data b WHERE b.imsi = :imsi");
		query.setParameter("imsi", imsi);
		List<Base_data> baseDataList = query.getResultList();
		if(baseDataList.isEmpty()) {
			List<IMSIWithEventIDAndCauseCode> imsiWithEventIDAndCauseCodeList = new ArrayList<>(0);
			return imsiWithEventIDAndCauseCodeList;
		}
		imsiWithEventIDAndCauseCodeFactory = new IMSIWithEventIDAndCauseCodeFactory(baseDataList);
		List<IMSIWithEventIDAndCauseCode> imsiWithEventIDAndCauseCodeList = 
				imsiWithEventIDAndCauseCodeFactory.getIMSIWithEventIDAndCauseCodeList();
		return imsiWithEventIDAndCauseCodeList;
	}
	public List<Base_data> getAllBaseDataBetweenDates(String date1, String date2) {
		dateParser = new DateParser();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
		query = entityManager.createQuery("SELECT i FROM Base_data i WHERE i.date_time BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", calendarArray[0]);
		query.setParameter("endDate", calendarArray[1]);
		List<Base_data> baseDataBetweenDates = query.getResultList();
		return baseDataBetweenDates;
	}
	
	public List<Base_data> getIMSIsForFailureClass(String failureClass) throws Exception {
		query = entityManager.createQuery("SELECT DISTINCT(b) FROM Base_data b WHERE b.failure_class = " + failureClass + " GROUP BY b.imsi");
		List<Base_data> baseDataList = query.getResultList();
		return baseDataList;
	}
	
	public List<Failure_class> getFailureClass() throws Exception {
		query = entityManager.createQuery("SELECT DISTINCT(b.failure_class) FROM Base_data b");
		List<Failure_class> failureClassList = query.getResultList();
		return failureClassList;
	}
	
	public List<UniqueEventCauseFailureClass> getUniqueCauseCodeAndDescriptionForFailureClassForIMSI(BigInteger imsi){
	
		query = entityManager.createQuery("SELECT DISTINCT b.event_cause.cause_code, b.failure_class.failure_class, b.failure_class.description FROM Base_data b" +
											" WHERE b.imsi = :imsi ");
		query.setParameter("imsi", imsi);
		List<Object> causeCodeFailureClassDescriptionList = query.getResultList();
		List<UniqueEventCauseFailureClass> causeCodeFailureClassDescriptionListOfHelperObjects = convertUniqueCauseCodeAndDescriptionForFailureClassObject(causeCodeFailureClassDescriptionList);
		
		
		return causeCodeFailureClassDescriptionListOfHelperObjects;

		
	}
	
	private List<UniqueEventCauseFailureClass> convertUniqueCauseCodeAndDescriptionForFailureClassObject(List<Object> causeCodeFailureClassDescriptData){
		
		int causeCode;
		int failureClass;
		String description;
		
		List<UniqueEventCauseFailureClass> causeCodeFailureClassDescriptObjectList = new ArrayList<UniqueEventCauseFailureClass>();
		for(Object object : causeCodeFailureClassDescriptData){
			Object[] objectArray = (Object[]) object;
			causeCode = (int) (objectArray[0]);
			failureClass = (int)(objectArray[1]);
			description = (String)(objectArray[2]);
			
			UniqueEventCauseFailureClass uniqueEventCauseFailureClassObject = new UniqueEventCauseFailureClass(causeCode, failureClass, description);
			causeCodeFailureClassDescriptObjectList.add(uniqueEventCauseFailureClassObject);
		}
	
		return causeCodeFailureClassDescriptObjectList;
	}

	
	

}
