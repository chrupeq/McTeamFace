package com.ait.imsiStats;

import java.math.BigDecimal;
import java.math.BigInteger;

public class IMSIStatsObjectFactory {
	
	public static IMSIStats getIMSIStatsInstance(BigInteger imsi, int failureDuration, int numberOfFailures) {
		return new IMSIStats(imsi, failureDuration, numberOfFailures);	
	}
	

}
