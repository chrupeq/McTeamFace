package com.ait.db.model;

import java.util.ArrayList;
import java.util.List;

public class TopTenMarketOperatorCellIdFactory {
	private List<Base_data> baseDataList;
	private List<TopTenMarketOperatorCellIdCombinations> topTenMarketOperatorCellIdCombsList;
	
	public TopTenMarketOperatorCellIdFactory(List<Base_data> baseDataList){
		this.baseDataList = baseDataList;
	}
	
	public List<TopTenMarketOperatorCellIdCombinations> getTopTenMarketOperatorCellIdList(){
		int marketMCC;
		int operatorMNC;
		String operatorDescription;
		int cellId = 0;
		int index = 0;
		
		
		
		topTenMarketOperatorCellIdCombsList = new ArrayList<>();
		for(Base_data baseData : baseDataList) {
			System.out.println("Iteration number: " + ++index);
			
			marketMCC = baseData.getMcc_mnc().getMcc();
			operatorMNC = baseData.getMcc_mnc().getMnc();
			operatorDescription = baseData.getMcc_mnc().getOperator();
			
			cellId = baseData.getCell_id();
		
			TopTenMarketOperatorCellIdCombinations topTenMarketOperatorCellIdCombinations = 
					new TopTenMarketOperatorCellIdCombinations(marketMCC, operatorMNC, operatorDescription, cellId);
			
			topTenMarketOperatorCellIdCombsList.add(topTenMarketOperatorCellIdCombinations);

		}
		
		return topTenMarketOperatorCellIdCombsList;
	}
	
	
	
	
}




