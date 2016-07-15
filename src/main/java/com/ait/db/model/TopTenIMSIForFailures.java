package com.ait.db.model;

import java.math.BigInteger;

public class TopTenIMSIForFailures implements Comparable<TopTenIMSIForFailures>{
	private BigInteger IMSI;
	private Long failureCount;
	
	public TopTenIMSIForFailures(BigInteger IMSI, Long failureCount){
		this.IMSI = IMSI;
		this.failureCount = failureCount;
	}

	public BigInteger getIMSI() {
		return IMSI;
	}

	public Long getFailureCount() {
		return failureCount;
	}

	
	@Override
	public int compareTo(TopTenIMSIForFailures that) {
		
		return that.failureCount.compareTo(this.failureCount);
	}
	
}
