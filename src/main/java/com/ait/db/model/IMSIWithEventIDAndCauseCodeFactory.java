package com.ait.db.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IMSIWithEventIDAndCauseCodeFactory {
	private List<Base_data> baseDataList;
	private List<IMSIWithEventIDAndCauseCode> imsiWithEventIDAndCauseCodeList;
	private Set<IMSIWithEventIDAndCauseCode> imsiWithEventIDAndCauseCodeSet;
	
	public IMSIWithEventIDAndCauseCodeFactory(List<Base_data> baseDataList) {
		this.baseDataList = baseDataList;
	}
	
	private Set<IMSIWithEventIDAndCauseCode> getIMSIWithEventIDAndCauseCodeSet() {
		int eventId = 0;
		String causeCode;
		String failureClass;
		BigInteger imsi;
		String eventIdCauseCodeDescription;
		String failureDescription;
	
		imsiWithEventIDAndCauseCodeSet = new HashSet<>();
		for(Base_data baseData : baseDataList) {
			Object[] eventCauseKeys = processEventCause(baseData);
			eventId = (int) eventCauseKeys[0];
			causeCode = (String) eventCauseKeys[1];
			eventIdCauseCodeDescription = (String) eventCauseKeys[2];
			String[] failureClassArray = processFailureClass(baseData);
			failureClass = failureClassArray[0];
			failureDescription = failureClassArray[1];
			imsi = baseData.getImsi();
			IMSIWithEventIDAndCauseCode imsiWithEventIDAndCauseCode 
				= new IMSIWithEventIDAndCauseCode(eventId, causeCode, eventIdCauseCodeDescription, 
						failureClass, failureDescription, imsi);
			imsiWithEventIDAndCauseCodeSet.add(imsiWithEventIDAndCauseCode);

		}
		return imsiWithEventIDAndCauseCodeSet;
	}
	private Object[] processEventCause(Base_data baseData) {
		Integer eventId = null;
		String causeCode;
		String eventIdCauseCodeDescription;
		Object[] eventCauseKeyArray = new Object[3];
		if(baseData.getEvent_cause() != null) {
			eventId = baseData.getEvent_cause().getEvent_id();
			causeCode = Integer.toString(baseData.getEvent_cause().getCause_code());
			eventIdCauseCodeDescription = baseData.getEvent_cause().getDescription();
		} else {
			eventId = -1;
			causeCode = "Cause code could not be determined";
			eventIdCauseCodeDescription = "Unknown";
		}
		eventCauseKeyArray[0] = eventId;
		eventCauseKeyArray[1] = causeCode;
		eventCauseKeyArray[2] = eventIdCauseCodeDescription;
		return eventCauseKeyArray;
	}
	private String[] processFailureClass(Base_data baseData) {
		String failureClass;
		String failureDescription;
		String[] failureClassArray = new String[2];
		if(baseData.getFailure_class() == null) {
			failureClass = "Failure class could not be determined.";
			failureDescription = "Unknown";
		} else {
			failureClass = Integer.toString(baseData.getFailure_class().getFailure_class());
			failureDescription = baseData.getFailure_class().getDescription();
		}
		failureClassArray[0] = failureClass;
		failureClassArray[1] = failureDescription;
		return failureClassArray;
	}
	public List<IMSIWithEventIDAndCauseCode> getIMSIWithEventIDAndCauseCodeList(){
		Set<IMSIWithEventIDAndCauseCode> imsiWithEventIDAndCauseCodeSet = getIMSIWithEventIDAndCauseCodeSet();
		imsiWithEventIDAndCauseCodeList = new ArrayList<>();
		for(IMSIWithEventIDAndCauseCode imsiWithEventIDAndCauseCode : imsiWithEventIDAndCauseCodeSet){
			String causeCode = imsiWithEventIDAndCauseCode.getCause_code();
//			causeCode = editCauseCode(causeCode);
			imsiWithEventIDAndCauseCode.setCause_code(causeCode);
			imsiWithEventIDAndCauseCodeList.add(imsiWithEventIDAndCauseCode);
		}
		return imsiWithEventIDAndCauseCodeList;
	}
	
//	public String editCauseCode(String setMe){
//		
//		if(setMe.length() == 5){
//			return setMe.substring(0, 1);
//		}else{
//			return setMe.substring(0, 2);
//		}
//		
//	}
}
