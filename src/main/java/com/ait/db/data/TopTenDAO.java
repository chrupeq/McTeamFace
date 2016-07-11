package com.ait.db.data;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.db.model.Base_data;
import com.ait.db.model.MarketOperatorCellIdCounter;
import com.ait.db.model.TopTenMarketOperatorCellIdCombinations;
import com.ait.db.model.TopTenMarketOperatorCellIdFactory;

@Stateless
@LocalBean
public class TopTenDAO {

	@PersistenceContext
    private EntityManager entityManager;
	private Query query;
	private DateParser dateParser;
	private TopTenMarketOperatorCellIdFactory topTenMarketOperatorCellIdFactory;



public List<TopTenMarketOperatorCellIdCombinations> getTopTenMarketOperatorCellIdCombinationsWithFailures(String date1, String date2){
	dateParser = new DateParser();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
	
	query = entityManager.createQuery("SELECT i FROM Base_data i WHERE i.date_time BETWEEN :startDate AND :endDate");
	query.setParameter("startDate", calendarArray[0]);
	query.setParameter("endDate", calendarArray[1]);
	List<Base_data> DataBetweenDates = query.getResultList();
	
	topTenMarketOperatorCellIdFactory = new TopTenMarketOperatorCellIdFactory(DataBetweenDates);
	List<TopTenMarketOperatorCellIdCombinations> requiredAttributesFromBaseData = topTenMarketOperatorCellIdFactory.getTopTenMarketOperatorCellIdList();
	List<TopTenMarketOperatorCellIdCombinations> topTenForFailures = new MarketOperatorCellIdCounter(requiredAttributesFromBaseData).getTopTenMarketOperatorCellIdWithFailureCount();
	
	System.out.println("list size" + topTenForFailures.size());
	
	return topTenForFailures;
}	
	
	
}