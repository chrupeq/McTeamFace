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
import com.ait.db.model.IMSIHelperObject;
import com.ait.db.model.IMSIWithFailuresFactory;
import com.ait.db.model.IMSIWithValidFailureClasses;
import com.ait.db.model.UniqueEventCauseFailureClass;
import com.ait.imsiStats.IMSIStats;
import com.ait.imsiStats.IMSIStatsObjectFactory;

@Stateless
@LocalBean
public class IMSIDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private Query query;
	private DateParser dateParser;
	//private IMSIWithEventIDAndCauseCodeFactory imsiWithEventIDAndCauseCodeFactory;

	public List<BigInteger> getAllUniqueIMSIs() {
		query = entityManager.createQuery("SELECT DISTINCT(i.imsi) FROM Base_data i");
		final List<BigInteger> imsisAsBigInts = query.getResultList();
		return imsisAsBigInts;
	}

	public List<Base_data> getAllBaseDataByIMSIAndDate(final BigInteger imsi, final String dateOne, final String dateTwo) {
		dateParser = new DateParser();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, dateOne, dateTwo);
		query = entityManager.createQuery(
				"SELECT b FROM Base_data b WHERE b.imsi = :imsi AND b.date_time BETWEEN :startDate AND :endDate");
		query.setParameter("imsi", imsi);
		query.setParameter("startDate", calendarArray[0]);
		query.setParameter("endDate", calendarArray[1]);
		final List<Base_data> baseDataList = query.getResultList();
		System.out.println(baseDataList.size());
		return baseDataList;

	}

	public List<IMSIWithValidFailureClasses> getIMSIsByDates(final String date1, final String date2) {

		dateParser = new DateParser();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);

		query = entityManager
				.createQuery("SELECT i FROM Base_data i WHERE i.date_time BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", calendarArray[0]);
		query.setParameter("endDate", calendarArray[1]);
		final List<Base_data> IMSIsBetweenDates = query.getResultList();
		final List<IMSIWithValidFailureClasses> imsiWithValidFailureClasses = IMSIWithFailuresFactory
				.getImsiFailureClassList(IMSIsBetweenDates);
		System.out.println("failures" + imsiWithValidFailureClasses.size());
		Collections.sort(imsiWithValidFailureClasses);
		return imsiWithValidFailureClasses;
	}

	public List<Object> getIMSIsWithEventIDsAndCauseCodes(final BigInteger imsi) {
		// query = entityManager.createQuery("SELECT b FROM Base_data b WHERE
		// b.imsi = :imsi");
		query = entityManager.createQuery("SELECT b FROM Base_data b WHERE b.imsi = :imsi GROUP BY event_cause.event_id, event_cause.cause_code	");
		query.setParameter("imsi", imsi);
		final List<Object> objectDataList = query.getResultList();
		// if(baseDataList.isEmpty()) {
		// List<IMSIWithEventIDAndCauseCode> imsiWithEventIDAndCauseCodeList =
		// new ArrayList<>(0);
		// return imsiWithEventIDAndCauseCodeList;
		// }
		// imsiWithEventIDAndCauseCodeFactory = new
		// IMSIWithEventIDAndCauseCodeFactory(baseDataList);
		// List<IMSIWithEventIDAndCauseCode> imsiWithEventIDAndCauseCodeList =
		// imsiWithEventIDAndCauseCodeFactory.getIMSIWithEventIDAndCauseCodeList();
		return objectDataList;
	}

	public List<Base_data> getAllBaseDataBetweenDates(final String date1, final String date2) {
		dateParser = new DateParser();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
		query = entityManager
				.createQuery("SELECT i FROM Base_data i WHERE i.date_time BETWEEN :startDate AND :endDate");
		query.setParameter("startDate", calendarArray[0]);
		query.setParameter("endDate", calendarArray[1]);
		final List<Base_data> baseDataBetweenDates = query.getResultList();
		return baseDataBetweenDates;
	}

	public List<Base_data> getIMSIsForFailureClass(final String failureClass) {
		query = entityManager.createQuery(
				"SELECT DISTINCT(b) FROM Base_data b WHERE b.failure_class = " + failureClass + " GROUP BY b.imsi");
		final List<Base_data> baseDataList = query.getResultList();
		return baseDataList;
	}

	public List<Failure_class> getFailureClass() {
		query = entityManager.createQuery("SELECT DISTINCT(b.failure_class) FROM Base_data b");
		final List<Failure_class> failureClassList = query.getResultList();
		return failureClassList;
	}

	public List<UniqueEventCauseFailureClass> getUniqueCauseCodeAndDescriptionForFailureClassForIMSI(final BigInteger imsi) {
		
		query = entityManager.createQuery(
				"SELECT DISTINCT b.event_cause.cause_code, b.failure_class.failure_class, b.failure_class.description FROM Base_data b"
						+ " WHERE b.imsi = :imsi ");
		query.setParameter("imsi", imsi);
		final List<Object> causeCodeFailureClassDescriptionList = query.getResultList();
		final List<UniqueEventCauseFailureClass> causeCodeFailureClassDescriptionListOfHelperObjects = convertUniqueCauseCodeAndDescriptionForFailureClassObject(
				causeCodeFailureClassDescriptionList);

		return causeCodeFailureClassDescriptionListOfHelperObjects;

	}

	private List<UniqueEventCauseFailureClass> convertUniqueCauseCodeAndDescriptionForFailureClassObject(
			final List<Object> causeCodeFailureClassDescriptData) {

		int causeCode;
		int failureClass;
		String description;

		final List<UniqueEventCauseFailureClass> causeCodeFailureClassDescriptObjectList = new ArrayList<UniqueEventCauseFailureClass>();
		for (final Object object : causeCodeFailureClassDescriptData) {
			final Object[] objectArray = (Object[]) object;
			causeCode = (int) (objectArray[0]);
			failureClass = (int) (objectArray[1]);
			description = (String) (objectArray[2]);

			final UniqueEventCauseFailureClass uniqueEventCauseFailureClassObject = new UniqueEventCauseFailureClass(
					causeCode, failureClass, description);
			causeCodeFailureClassDescriptObjectList.add(uniqueEventCauseFailureClassObject);
		}

		return causeCodeFailureClassDescriptObjectList;
	}

	public List<IMSIHelperObject> getAffectedIMSIsPerFailureClass(final int failureClass) {
		query = entityManager.createQuery(
				"SELECT DISTINCT (b.imsi) FROM Base_data b WHERE b.failure_class.failure_class = :failureClass ORDER BY (b.imsi)");
		query.setParameter("failureClass", failureClass);
		final List<Object> IMSIsPerFailureClass = query.getResultList();
		return getIMSIObjects(IMSIsPerFailureClass);
	}

	public List<IMSIHelperObject> getIMSIObjects(final List<Object> IMSIObjects) {
		BigInteger imsi;
		IMSIHelperObject imsiObject;
		final List<IMSIHelperObject> imsiObjects = new ArrayList<>();
		for (final Object bigIntObj : IMSIObjects) {
			imsi = (BigInteger) bigIntObj;
			imsiObject = new IMSIHelperObject(imsi);
			imsiObjects.add(imsiObject);
		}
		return imsiObjects;
	}

	public List<IMSIStats> getIMSIStatistics(final String date1, final String date2) {
		dateParser = new DateParser();
		final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		final Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
		query = entityManager.createQuery(
				"SELECT b.imsi, COUNT(*) AS num_of_failures, SUM(b.duration) AS failure_duration FROM Base_data b "
						+ "WHERE b.date_time BETWEEN :startDate AND :endDate GROUP BY (b.imsi) ORDER BY failure_duration DESC");
		query.setParameter("startDate", calendarArray[0]);
		query.setParameter("endDate", calendarArray[1]);
		final List<Object> imsiStats = query.getResultList();
		return turnQueryObjectsIntoIMSIStatsObjects(imsiStats);
	}

	private List<IMSIStats> turnQueryObjectsIntoIMSIStatsObjects(final List<Object> imsiStats) {
		final List<IMSIStats> imsiStatsObjects = new ArrayList<>();
		Object[] imsiStatsQueryObject;
		BigInteger imsi;
		long numberOfFailures;
		long failureDuration;
		for (final Object object : imsiStats) {
			imsiStatsQueryObject = (Object[]) object;
			imsi = (BigInteger) imsiStatsQueryObject[0];
			numberOfFailures = (long) imsiStatsQueryObject[1];
			failureDuration = (long) imsiStatsQueryObject[2];
			final IMSIStats imsiStatsObject = IMSIStatsObjectFactory.getIMSIStatsInstance(imsi, failureDuration,
					numberOfFailures);
			imsiStatsObjects.add(imsiStatsObject);
		}
		Collections.sort(imsiStatsObjects);
		return imsiStatsObjects;
	}
}
