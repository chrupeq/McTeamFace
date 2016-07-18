package com.ait.imsiStats;

import java.math.BigInteger;

public class IMSIStats implements Comparable<IMSIStats> {
	private BigInteger imsi;
	private long failureDuration;
	private long numberOfFailures;
	
	IMSIStats() {
		
	}
	IMSIStats(BigInteger imsi, int numberOfFailures) {
		this.imsi = imsi;
		this.numberOfFailures = numberOfFailures;
	}
	IMSIStats(BigInteger imsi, long failureDuration, long numberOfFailures) {
		super();
		this.imsi = imsi;
		this.failureDuration = failureDuration;
		this.numberOfFailures = numberOfFailures;
	}

	public BigInteger getImsi() {
		return imsi;
	}
	public long getFailureDuration() {
		return failureDuration;
	}
	public long getNumberOfFailures() {
		return numberOfFailures;
	}
	@Override
	public int compareTo(IMSIStats o) {
		return this.imsi.compareTo(o.imsi);
	}
}
