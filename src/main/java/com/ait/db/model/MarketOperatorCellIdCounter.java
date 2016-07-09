package com.ait.db.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarketOperatorCellIdCounter {
	private List<TopTenMarketOperatorCellIdCombinations> topTenMarketOperatorCellIdCombsList;
	private Map<TopTenMarketOperatorCellIdCombinations, Integer> numOfFailures;
	
	
	public MarketOperatorCellIdCounter( List<TopTenMarketOperatorCellIdCombinations> topTenMarketOperatorCellIdCombsList){
		this.topTenMarketOperatorCellIdCombsList = topTenMarketOperatorCellIdCombsList;
		
	}
	
	private Map<TopTenMarketOperatorCellIdCombinations, Integer> countNumberOfFailures(){
		numOfFailures = new HashMap<>();
		int failureCount;
		
		for(TopTenMarketOperatorCellIdCombinations topTen : topTenMarketOperatorCellIdCombsList){
			
			if(numOfFailures.containsKey(topTen)){
				failureCount = topTen.getFailureCount();
				failureCount++;
				numOfFailures.put(topTen, failureCount);
			}else{
				numOfFailures.put(topTen, 1);
			}
			
		}
		
		return numOfFailures;
	}
	
	public List<TopTenMarketOperatorCellIdCombinations> getTopTenMarketOperatorCellIdWithFailureCount() {
		numOfFailures =  countNumberOfFailures();
		List<TopTenMarketOperatorCellIdCombinations> resultWithFailureCounts = new ArrayList<>();

		
		for(TopTenMarketOperatorCellIdCombinations key : numOfFailures.keySet()){
			int numberOfFailures = key.getFailureCount();
			key.setFailureCount(numberOfFailures);
			
			resultWithFailureCounts.add(key);
		}
		
		Collections.sort(resultWithFailureCounts);
		resultWithFailureCounts = resultWithFailureCounts.subList(0, 10);

		return resultWithFailureCounts;
	}
}
