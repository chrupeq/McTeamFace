package com.ait.db.model;

public class TopTenMarketOperatorCellIdCombinations {

	private String marketing_name;
	private String operator;
	private int cell_id;
	String manufacturer;
	private int failureCount;
	
	public TopTenMarketOperatorCellIdCombinations(String marketing_name, String manufacturer,
			String operator, int cell_id){
		this.marketing_name=marketing_name;
		this.operator=operator;
		this.manufacturer = manufacturer;
		this.cell_id=cell_id;
		this.failureCount=failureCount;
	}
	
	public String getMarketing_name(){
		return marketing_name;
	}
	
	public String getOperator(){
		return operator;
	}
	
	public int getCell_id(){
		return cell_id;
	}
	
	public int getFailure_count(){
		return failureCount;
	}
}


//The failure Count has been taken out of the constructor here!!!!