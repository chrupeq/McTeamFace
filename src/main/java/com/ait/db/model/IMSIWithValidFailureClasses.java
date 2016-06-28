package com.ait.db.model;

import java.math.BigInteger;
import java.util.Calendar;

public class IMSIWithValidFailureClasses {
	private Calendar dateTime;
	private int failureClass;
	private BigInteger imsi;
	
	public IMSIWithValidFailureClasses(Calendar dateTime, int failureClass, BigInteger imsi) {
		this.dateTime = dateTime;
		this.failureClass = failureClass;
		this.imsi = imsi;
	}
	
	public Calendar getDate_time() {
		return dateTime;
	}
	public int getFailureClass() {
		return failureClass;
	}
	public BigInteger getImsi() {
		return imsi;
	}
	
	

}
