package com.ait.imsiStats;

import java.math.BigDecimal;
import java.math.BigInteger;

import com.ait.db.data.DateParser;

public class IMSIStatsObjectFactory {
	
	public static IMSIStats getIMSIStatsInstance(BigInteger imsi, int failureDuration, int numberOfFailures) {
		return new IMSIStats(imsi, failureDuration, numberOfFailures);	
	}
	public static IMSIStats getIMSIStatsInstance(BigInteger imsi, int numberOfFailures) {
		return new IMSIStats(imsi, numberOfFailures);
	}

}
