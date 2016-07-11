package com.ait.db.model;

public class TopTenMarketOperatorCellIdCombinations implements Comparable<TopTenMarketOperatorCellIdCombinations>{

	private int marketMCC;
	private int operatorMNC;
	private int cellId;
	private String operatorDescription;
	private String country;
	private Long failureCount;
	
	public TopTenMarketOperatorCellIdCombinations(int marketMCC, int operatorMNC,
			String operatorDescription, String country, int cellId, long failureCount){
		this.marketMCC=marketMCC;
		this.operatorMNC=operatorMNC;
		this.operatorDescription = operatorDescription;
		this.cellId=cellId;
		this.failureCount = failureCount;
		this.country = country;
		
	}
	
	public String getCountry() {
		return country;
	}


	public int getMarketMCC(){
		return marketMCC;
	}
	
	public int getOperatorMNC(){
		return operatorMNC;
	}

	public int getCell_id() {
		return cellId;
	}

	public String getOperatorDescription() {
		return operatorDescription;
	}

	public Long getFailureCount() {
		return failureCount;
	}
	
	@Override
	public int compareTo(TopTenMarketOperatorCellIdCombinations that) {
		
		return that.failureCount.compareTo(this.failureCount);
	}
	
	
	
}	
