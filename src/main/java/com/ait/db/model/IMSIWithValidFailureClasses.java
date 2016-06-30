package com.ait.db.model;

import java.math.BigInteger;
import java.util.Calendar;

public class IMSIWithValidFailureClasses implements Comparable<IMSIWithValidFailureClasses> {
	private int report_id;
	private String dateTime;
	private int failureClass;
	private BigInteger imsi;
	
	public IMSIWithValidFailureClasses (int reportId, String dateTime, int failureClass, BigInteger imsi) {
		this.report_id = reportId;
		this.dateTime = dateTime;
		this.failureClass = failureClass;
		this.imsi = imsi;
	}
	public int getReportID() {
		return report_id;
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
	@Override
	public int compareTo(IMSIWithValidFailureClasses o) {
		return this.imsi.compareTo(o.imsi);
	}
}
