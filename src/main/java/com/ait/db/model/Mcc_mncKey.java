package com.ait.db.model;

import java.io.Serializable;

public class Mcc_mncKey implements Serializable, CompositePK {

	private static final long serialVersionUID = 1L;
	private int mcc;
	private int mnc;
	
	
	
	public Mcc_mncKey() {
	}

	public Mcc_mncKey(int mcc, int mnc) {
		super();
		this.mcc = mcc;
		this.mnc = mnc;
	}
	
	public int getMcc() {
		return mcc;
	}

	public void setMcc(int mcc) {
		this.mcc = mcc;
	}

	public int getMnc() {
		return mnc;
	}

	public void setMnc(int mnc) {
		this.mnc = mnc;
	}
	
	public int getUniqueIdentifier() {
		return this.mcc + this.mnc;
	}
	
	@Override
	public boolean equals(Object object) {
		if(object instanceof Mcc_mncKey) {
			Mcc_mncKey other = (Mcc_mncKey) object;
			return this.getUniqueIdentifier() == other.getUniqueIdentifier();
		}
		return false;
	}
	@Override
	public int hashCode() {
		String sumOfMccAndMnc = Integer.toString(this.getUniqueIdentifier());
		return sumOfMccAndMnc.hashCode();
	}
	
}
