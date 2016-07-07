package com.ait.db.model;

public class TopTenMarketOperatorCellIdCombinations {

	private int marketMCC;
	private int operatorMNC;
	private int cell_id;
	private String operatorDescription;
	private int failureCount;
	
	public TopTenMarketOperatorCellIdCombinations(int marketMCC, int operatorMNC,
			String operatorDescription, int cell_id){
		this.marketMCC=marketMCC;
		this.operatorMNC=operatorMNC;
		this.operatorDescription = operatorDescription;
		this.cell_id=cell_id;
		this.failureCount=failureCount;
	}
	
	public int getMarketMCC(){
		return marketMCC;
	}
	
	public int getOperatorMNC(){
		return operatorMNC;
	}

	public int getCell_id() {
		return cell_id;
	}

	public String getOperatorDescription() {
		return operatorDescription;
	}

	public int getFailureCount() {
		return failureCount;
	}

	
	

	
}	

//The failure Count has been taken out of the constructor here!!!!