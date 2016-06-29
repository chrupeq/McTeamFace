package com.ait.db.model;

import java.math.BigInteger;
import java.util.Calendar;

public class IMSIWithValidFailureClasses {
	private int reportId;
	private String dateTime;
	private int failureClass;
	private BigInteger imsi;
	
	public IMSIWithValidFailureClasses(int reportId, String dateTime, int failureClass, BigInteger imsi) {
		this.reportId = reportId;
		this.dateTime = dateTime;
		this.failureClass = failureClass;
		this.imsi = imsi;
	}
	public int getReportID() {
		return reportId;
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
