package com.ait.db.model;

public class TopTenMarketOperatorCellIdCombinations implements Comparable<TopTenMarketOperatorCellIdCombinations>{

	private int marketMCC;
	private int operatorMNC;
	private int cellId;
	private String operatorDescription;
	private Integer failureCount;
	
	public TopTenMarketOperatorCellIdCombinations(int marketMCC, int operatorMNC,
			String operatorDescription, int cellId){
		this.marketMCC=marketMCC;
		this.operatorMNC=operatorMNC;
		this.operatorDescription = operatorDescription;
		this.cellId=cellId;
		
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

	public Integer getFailureCount() {
		return failureCount;
	}
	
	public void setFailureCount(Integer failureCount) {
		this.failureCount = failureCount;
	}


	public int getUniqueIdentifier() {
		return this.marketMCC + this.operatorMNC + this.cellId;
	}
	
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof TopTenMarketOperatorCellIdCombinations) {
			TopTenMarketOperatorCellIdCombinations other = (TopTenMarketOperatorCellIdCombinations) object;
			return this.getUniqueIdentifier() == other.getUniqueIdentifier();
		}
		return false;
	}

	@Override 
	public int hashCode() {
		String sumOfMccMncCellId = Integer.toString(this.getUniqueIdentifier());
		return sumOfMccMncCellId.hashCode();
	}


	@Override
	public int compareTo(TopTenMarketOperatorCellIdCombinations that) {
		
		return that.failureCount.compareTo(this.failureCount);
	}
	
	
	
}	

//The failure Count has been taken out of the constructor here!!!!