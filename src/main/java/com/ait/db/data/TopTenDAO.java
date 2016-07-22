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
import com.ait.db.model.TopTenIMSIForFailures;
import com.ait.db.model.TopTenMarketOperatorCellIdCombinations;


@Stateless
@LocalBean
public class TopTenDAO {

	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	private DateParser dateParser;
	


public List<TopTenMarketOperatorCellIdCombinations> getTopTenMarketOperatorCellIdCombinationsWithFailures(final String date1, final String date2){
	dateParser = new DateParser();
	final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
	
	query = entityManager.createQuery("SELECT COUNT(b.report_id) AS NUM_ROWS, b.mcc_mnc.mcc, b.mcc_mnc.mnc, b.mcc_mnc.country, b.mcc_mnc.operator, b.cell_id FROM Base_data b WHERE b.date_time BETWEEN :startDate AND :endDate GROUP BY b.mcc_mnc.mcc, b.mcc_mnc.mnc, b.cell_id ORDER BY NUM_ROWS DESC LIMIT 10");
	
	
	
	query.setParameter("startDate", calendarArray[0]);
	query.setParameter("endDate", calendarArray[1]);
	final List<Object> topTenData = query.getResultList();
	final List<TopTenMarketOperatorCellIdCombinations> topTenListOfHelperObjects = convertToTopTenObject(topTenData);
	
	
	System.out.println(calendarArray[0]+ "hello");
	System.out.println(calendarArray[1]);
	return topTenListOfHelperObjects;
}	

private List<TopTenMarketOperatorCellIdCombinations> convertToTopTenObject(final List<Object> topTenData){
	long numOfFailures;
	int market;
	int operator;
	String country;
	String operatorDescription;
	int cellID;
	List<TopTenMarketOperatorCellIdCombinations> topTenList = new ArrayList<TopTenMarketOperatorCellIdCombinations>();
	
	for(final Object object : topTenData){
		final Object[] objectArray = (Object[]) object;
		numOfFailures = (long) (objectArray[0]);
		market = (int)(objectArray[1]);
		operator = (int)(objectArray[2]);
		country = (String) (objectArray[3]);
		operatorDescription = (String)(objectArray[4]);
		cellID = (int)(objectArray[5]);
		
		final TopTenMarketOperatorCellIdCombinations topTenMarketOperatorCellIdCombinations 
		= new TopTenMarketOperatorCellIdCombinations(market, operator, operatorDescription, country, cellID, numOfFailures);
		
		topTenList.add(topTenMarketOperatorCellIdCombinations);
	}
	
	Collections.sort(topTenList);
	if(topTenList.size() > 10) {
		topTenList = topTenList.subList(0, 10);
	}

	return topTenList;
	}


public List<TopTenIMSIForFailures> getTopTenIMSIForFailures(final String date1, final String date2){
	dateParser = new DateParser();
	final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
	
	query = entityManager.createQuery("SELECT COUNT(b.report_id) AS NUM_ROWS, b.imsi FROM Base_data b WHERE b.date_time BETWEEN :startDate AND :endDate GROUP BY b.imsi ORDER BY NUM_ROWS DESC LIMIT 10");
	
	
	query.setParameter("startDate", calendarArray[0]);
	query.setParameter("endDate", calendarArray[1]);
	final List<Object> topTenIMSIData = query.getResultList();
	final List<TopTenIMSIForFailures> topTenIMSIListOfHelperObjects = convertToTopTenIMSIObject(topTenIMSIData);
	
	return topTenIMSIListOfHelperObjects;
}	


private List<TopTenIMSIForFailures> convertToTopTenIMSIObject(final List<Object> topTenIMSIData){
	BigInteger IMSI;
	long numOfFailures;
	
	List<TopTenIMSIForFailures> topTenIMSIList = new ArrayList<TopTenIMSIForFailures>();
	for(final Object object : topTenIMSIData){
		final Object[] objectArray = (Object[]) object;
		numOfFailures = (long) (objectArray[0]);
		IMSI = (BigInteger)(objectArray[1]);
		
		final TopTenIMSIForFailures topTenIMSIForFailures = new TopTenIMSIForFailures(IMSI, numOfFailures);
		topTenIMSIList.add(topTenIMSIForFailures);
	}
	
	Collections.sort(topTenIMSIList);
	if(topTenIMSIList.size() > 10) {
		topTenIMSIList = topTenIMSIList.subList(0, 10);
	}
	return topTenIMSIList;
}


































}