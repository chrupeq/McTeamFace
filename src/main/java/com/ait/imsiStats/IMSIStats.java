package com.ait.imsiStats;

import java.math.BigInteger;

public class IMSIStats implements Comparable<IMSIStats> {
	private BigInteger imsi;
	private int failureDuration;
	private int numberOfFailures;
	
	IMSIStats() {
		
	}
	IMSIStats(BigInteger imsi, int numberOfFailures) {
		this.imsi = imsi;
		this.numberOfFailures = numberOfFailures;
	}
	IMSIStats(BigInteger imsi, int failureDuration, int numberOfFailures) {
		super();
		this.imsi = imsi;
		this.failureDuration = failureDuration;
		this.numberOfFailures = numberOfFailures;
	}

	public BigInteger getImsi() {
		return imsi;
	}
	public int getFailureDuration() {
		return failureDuration;
	}
	public int getNumberOfFailures() {
		return numberOfFailures;
	}
	@Override
	public int compareTo(IMSIStats o) {
		return this.imsi.compareTo(o.imsi);
	}
}
