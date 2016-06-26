package com.ait.imsiStats;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.ait.db.model.Base_data;
import com.ait.db.model.NetworkEntity;

public class IMSIStatsProducer {
	private List<? extends NetworkEntity> baseDataList;
	private Map<BigInteger, Integer> numberOfFailures;
	private Map<BigInteger, Integer> durationOfFailures;
	private int failuresCounter;
	private BigInteger imsi;
	
	@SuppressWarnings("unchecked")
	public IMSIStatsProducer(List<? extends NetworkEntity> baseDataList) {
		this.baseDataList = baseDataList;
	}
	public int getLengthOfList() {
		return baseDataList.size();
	}
	public Map<BigInteger, Integer> countTheNumberOfFailures() {
		numberOfFailures = new HashMap<>();
		for(NetworkEntity baseData : baseDataList) {
			if(baseData instanceof Base_data) {
				imsi =  ((Base_data) baseData).getImsi();
				if(numberOfFailures.containsKey(imsi)) {
					failuresCounter = numberOfFailures.get(imsi);
					failuresCounter++;
					numberOfFailures.put(imsi, failuresCounter);
				} else {
					numberOfFailures.put(imsi, 1);
				}	
			} else {
				return numberOfFailures;
			}
		}
		return numberOfFailures;
	}
	public Map<BigInteger, Integer> calculateTotalFailureTime() {
		Map<BigInteger, Integer> numOfFailures = countTheNumberOfFailures();
		durationOfFailures = new HashMap<>();
		if(!numOfFailures.isEmpty()) {
			Set<BigInteger> imsiKeys = numOfFailures.keySet();
			int failureDuration = 0;
			for(BigInteger imsiKey : imsiKeys) {
				for(NetworkEntity baseData : baseDataList) {
					if(baseData instanceof Base_data) {
						imsi =  ((Base_data) baseData).getImsi();
						if(imsiKey.equals(imsi)) {
							failureDuration += ((Base_data) baseData).getDuration();
						} else {
							continue;
						}
					} else {
						return durationOfFailures;
					}
				}
				durationOfFailures.put(imsiKey, failureDuration);
				failureDuration = 0;
			}
			return durationOfFailures;
		} else {
			return durationOfFailures;
		}	
	}
	
	
}
