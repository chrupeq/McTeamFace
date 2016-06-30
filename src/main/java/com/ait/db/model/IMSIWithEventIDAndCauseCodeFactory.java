package com.ait.db.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class IMSIWithEventIDAndCauseCodeFactory {
	private List<Base_data> baseDataList;
	private List<IMSIWithEventIDAndCauseCode> imsiWithEventIDAndCauseCodeList;
	
	public IMSIWithEventIDAndCauseCodeFactory(List<Base_data> baseDataList) {
		this.baseDataList = baseDataList;
	}
	
	public List<IMSIWithEventIDAndCauseCode> getIMSIWithEventIDAndCauseCodeList() {
		int eventId = 0;
		String causeCode;
		String failureClass;
		BigInteger imsi;
		int index = 0;
		imsiWithEventIDAndCauseCodeList = new ArrayList<>();
		for(Base_data baseData : baseDataList) {
			System.out.println("Iteration number " + ++index);
			if(baseData.getEvent_cause() != null) {
				eventId = baseData.getEvent_cause().getEvent_id();
				causeCode = Integer.toString(baseData.getEvent_cause().getCause_code());
			} else {
				causeCode = "Cause code could not be determined";
			}
			if(baseData.getFailure_class() == null) {
				failureClass = "Failure class could not be determined.";
			} else {
				failureClass = Integer.toString(baseData.getFailure_class().getFailure_class());
			}
			imsi = baseData.getImsi();
			IMSIWithEventIDAndCauseCode imsiWithEventIDAndCauseCode 
				= new IMSIWithEventIDAndCauseCode(eventId, causeCode, failureClass, imsi);
			imsiWithEventIDAndCauseCodeList.add(imsiWithEventIDAndCauseCode);
		}
		return imsiWithEventIDAndCauseCodeList;
	}

}
