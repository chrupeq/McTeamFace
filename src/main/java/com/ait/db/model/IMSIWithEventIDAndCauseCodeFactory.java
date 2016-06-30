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
			Object[] eventCauseKeys = processEventCause(baseData);
			eventId = (int) eventCauseKeys[0];
			causeCode = (String) eventCauseKeys[1];
			failureClass = processFailureClass(baseData);
			imsi = baseData.getImsi();
			IMSIWithEventIDAndCauseCode imsiWithEventIDAndCauseCode 
				= new IMSIWithEventIDAndCauseCode(eventId, causeCode, failureClass, imsi);
			imsiWithEventIDAndCauseCodeList.add(imsiWithEventIDAndCauseCode);
		}
		return imsiWithEventIDAndCauseCodeList;
	}
	private Object[] processEventCause(Base_data baseData) {
		Integer eventId = null;
		String causeCode;
		Object[] eventCauseKeyArray = new Object[2];
		if(baseData.getEvent_cause() != null) {
			eventId = baseData.getEvent_cause().getEvent_id();
			causeCode = Integer.toString(baseData.getEvent_cause().getCause_code());
		} else {
			eventId = -1;
			causeCode = "Cause code could not be determined";
		}
		eventCauseKeyArray[0] = eventId;
		eventCauseKeyArray[1] = causeCode;
		return eventCauseKeyArray;
	}
	private String processFailureClass(Base_data baseData) {
		String failureClass;
		if(baseData.getFailure_class() == null) {
			failureClass = "Failure class could not be determined.";
		} else {
			failureClass = Integer.toString(baseData.getFailure_class().getFailure_class());
		}
		return failureClass;
	}
}
