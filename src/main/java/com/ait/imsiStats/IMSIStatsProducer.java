package com.ait.imsiStats;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ait.db.model.Base_data;
import com.ait.db.model.NetworkEntity;

public class IMSIStatsProducer {
	private List<? extends NetworkEntity> baseDataList;
	private Map<BigInteger, Integer> numberOfFailures;
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
	
	
	
}
