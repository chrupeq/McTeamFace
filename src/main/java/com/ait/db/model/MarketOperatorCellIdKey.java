package com.ait.db.model;

public class MarketOperatorCellIdKey {
	
	private int marketMCC;
	private int operatorMNC;
	private int cellId;
	
	public MarketOperatorCellIdKey(int marketMCC, int operatorMNC, int cellId){
		this.marketMCC=marketMCC;
		this.operatorMNC=operatorMNC;
		this.cellId = cellId;
	}
	
	public int getUniqueIdentifier() {
		return this.marketMCC + this.operatorMNC + this.cellId;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof MarketOperatorCellIdKey) {
			MarketOperatorCellIdKey other = (MarketOperatorCellIdKey) object;
			return this.getUniqueIdentifier() == other.getUniqueIdentifier();
		}
		return false;
	}

	@Override 
	public int hashCode() {
		String sumOfMccMncCellId = Integer.toString(this.getUniqueIdentifier());
		return sumOfMccMncCellId.hashCode();
	}
	
}
