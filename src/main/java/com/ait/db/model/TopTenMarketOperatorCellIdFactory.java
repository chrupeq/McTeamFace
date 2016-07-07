package com.ait.db.model;

import java.util.ArrayList;
import java.util.List;

public class TopTenMarketOperatorCellIdFactory {
	private List<Base_data> baseDataList;
	private List<TopTenMarketOperatorCellIdCombinations> TopTenMarketOperatorCellIdCombsList;
	
	public TopTenMarketOperatorCellIdFactory(List<Base_data> baseDataList){
		this.baseDataList = baseDataList;
	}
	
	public List<TopTenMarketOperatorCellIdCombinations> getTopTenMarketOperatorCellIdList(){
		int marketMCC;
		int operatorMNC;
		String operatorDescription;
		int cellId = 0;
		int index = 0;
		
		
		
		TopTenMarketOperatorCellIdCombsList = new ArrayList<>();
		for(Base_data baseData : baseDataList) {
			System.out.println("Iteration number: " + ++index);
			
			marketMCC = baseData.getMcc_mnc().getMcc();
			operatorMNC = baseData.getMcc_mnc().getMnc();
			operatorDescription = baseData.getMcc_mnc().getOperator();
			
			cellId = baseData.getCell_id();
		
			//MarketOperatorCellIdKey  = new MarketOperatorCellIdKey()
	
			TopTenMarketOperatorCellIdCombinations topTenMarketOperatorCellIdCombinations = 
					new TopTenMarketOperatorCellIdCombinations(marketMCC, operatorMNC, operatorDescription, cellId);
			
			TopTenMarketOperatorCellIdCombsList.add(topTenMarketOperatorCellIdCombinations);

		}
		
		return TopTenMarketOperatorCellIdCombsList;
	}
	
	
	
	
	
	
	
}




//private Object[] processMarket(Base_data baseData) {
//String marketingName;
//String manufacturer;
//
//Object[] marketingNameManufacturerArray = new Object[2];
//if(baseData.getUser_equipment() != null){
//	marketingName = baseData.getUser_equipment().getMarketing_name();
//	manufacturer = baseData.getUser_equipment().getManufacturer();
//}else{
//	marketingName = "Marketing name could not be determined";
//	manufacturer = "Manufacturer could not be determined";
//}
//
//marketingNameManufacturerArray[0] = marketingName;
//marketingNameManufacturerArray[0] = manufacturer;
//
//return marketingNameManufacturerArray;
//
//}
