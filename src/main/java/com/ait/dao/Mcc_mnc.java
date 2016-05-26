package com.ait.dao;

public class Mcc_mnc {
	int MCC;
	int MNC;
	String COUNTRY;
	String OPERATOR;

	public int getMCC() {
		return MCC;
	}

	public void setMCC(int mCC) {
		MCC = mCC;
	}

	public int getMNC() {
		return MNC;
	}

	public void setMNC(int mNC) {
		MNC = mNC;
	}

	public String getCOUNTRY() {
		return COUNTRY;
	}

	public void setCOUNTRY(String cOUNTRY) {
		COUNTRY = cOUNTRY;
	}

	public String getOPERATOR() {
		return OPERATOR;
	}

	public void setOPERATOR(String oPERATOR) {
		OPERATOR = oPERATOR;
	}
}
