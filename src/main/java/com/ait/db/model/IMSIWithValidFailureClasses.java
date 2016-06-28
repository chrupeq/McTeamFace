package com.ait.db.model;

import java.math.BigInteger;
import java.util.Calendar;

public class IMSIWithValidFailureClasses {
	private String dateTime;
	private int failureClass;
	private BigInteger imsi;
	
	public IMSIWithValidFailureClasses(String dateTime, int failureClass, BigInteger imsi) {
		this.dateTime = dateTime;
		this.failureClass = failureClass;
		this.imsi = imsi;
	}
	
	public String getDate_time() {
		return dateTime;
	}
	public int getFailureClass() {
		return failureClass;
	}
	public BigInteger getImsi() {
		return imsi;
	}
	
	

}
