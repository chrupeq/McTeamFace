package com.ait.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Mcc_mnc {
	
	@Id
	private int mcc;
	private int mnc;
	private String country;
	private String operator;
	
	public Mcc_mnc() {}

	public Mcc_mnc(int mcc, int mnc, String country, String operator) {
		this.mcc = mcc;
		this.mnc = mnc;
		this.country = country;
		this.operator = operator;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

}
