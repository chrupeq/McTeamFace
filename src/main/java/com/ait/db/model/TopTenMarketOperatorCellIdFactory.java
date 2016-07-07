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
		String marketingName;
		String manufacturer;
		String operator;
		int cellId = 0;
		int index = 0;
		
		
		TopTenMarketOperatorCellIdCombsList = new ArrayList<>();
		for(Base_data baseData : baseDataList) {
			System.out.println("Iteration number: " + ++index);
			
			Object[] marketKeys = processMarket(baseData);
			marketingName = (String) marketKeys[0];
			manufacturer = (String) marketKeys[1];
			
			operator = processOperator(baseData);
			cellId = processCellId(baseData);
			
			TopTenMarketOperatorCellIdCombinations topTenMarketOperatorCellIdCombinations = 
					new TopTenMarketOperatorCellIdCombinations(marketingName, manufacturer, operator, cellId);
			TopTenMarketOperatorCellIdCombsList.add(topTenMarketOperatorCellIdCombinations);

		}
		
		return TopTenMarketOperatorCellIdCombsList;
	}
	
	private Object[] processMarket(Base_data baseData) {
		String marketingName;
		String manufacturer;
		
		Object[] marketingNameManufacturerArray = new Object[2];
		if(baseData.getUser_equipment() != null){
			marketingName = baseData.getUser_equipment().getMarketing_name();
			manufacturer = baseData.getUser_equipment().getManufacturer();
		}else{
			marketingName = "Marketing name could not be determined";
			manufacturer = "Manufacturer could not be determined";
		}
		
		marketingNameManufacturerArray[0] = marketingName;
		marketingNameManufacturerArray[0] = manufacturer;
		
		return marketingNameManufacturerArray;
		
		}
	
	private String processOperator(Base_data baseData) {
		String operator;
		
		if(baseData.getMcc_mnc() != null) {
			operator = baseData.getMcc_mnc().getOperator();
		}else{
			operator = "Operator could not be determined";
		}
		
		return operator;
	}
	
	private int processCellId(Base_data baseData) {
		int cellId;
		
		if(baseData.getCell_id() != 0) {
			cellId = baseData.getCell_id();
		}else{
			cellId = -1;
		}
		
		return cellId;
	}
	
}
